package br.com.projeto.app.controller;

import java.sql.Connection;
import java.util.Objects;

import br.com.projeto.core.base.ClientException;
import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Reserva;
import br.com.projeto.core.service.ReservaService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class ReservaController extends Controller {
    ReservaService<Connection> reservaService;

    public ReservaController(ReservaService<Connection> reservaService) {
        this.reservaService = reservaService;

        this.registerRoute(HttpMethod.PUT, "/reserva/alterar/status", this.alterarStatus);
        this.registerRoute(HttpMethod.PUT, "/reserva/alterar/quarto", this.alterarQuarto);
    }

    private Handler alterarStatus = (req, res) -> {
        Reserva payload = null;

        try {
            payload = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Reserva.class);
        } catch (Exception e) {
            res.setStatus(400);

            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(payload.getStatus()) || Objects.isNull(payload.getReservaId())) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            this.reservaService.alterarStatus(conn, payload.getReservaId(), payload.getStatus());

            res.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce.getReason()), res);

                return;
            }

            res.setStatus(500);
        }
    };

    private Handler alterarQuarto = (req, res) -> {
        Reserva payload = null;

        try {
            payload = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Reserva.class);
        } catch (Exception e) {
            res.setStatus(400);

            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(payload.getQuartoId()) || Objects.isNull(payload.getReservaId())) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            this.reservaService.alterarQuarto(conn, payload.getReservaId(), payload.getQuartoId());

            res.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce.getReason()), res);

                return;
            }

            res.setStatus(500);
        }
    };

}
