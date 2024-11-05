package br.com.projeto.app.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.ClientException;
import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;
import br.com.projeto.core.service.ClienteService;
import br.com.projeto.core.service.HotelService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class ClienteController extends Controller {
    ClienteService<Connection> clienteService;
    HotelService<Connection> hotelService;

    public ClienteController(ClienteService<Connection> clienteService, HotelService<Connection> hotelService) {
        this.clienteService = clienteService;
        this.hotelService = hotelService;

        this.registerRoute(HttpMethod.GET, "/cliente/historico", this.retornarHistoricoCliente);
        this.registerRoute(HttpMethod.PUT, "/cliente/reserva/cancelar", this.cancelarReserva);
        this.registerRoute(HttpMethod.PUT, "/cliente/atualizar", this.atualizarDados);
        this.registerRoute(HttpMethod.GET, "/cliente/reserva/disponivel", this.verificarReservaDisponivel);
    }

    private Handler atualizarDados = (req, res) -> {
        Cliente payload = null;

        try {
            payload = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Cliente.class);
        } catch (Exception e) {
            res.setStatus(400);

            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);
        }

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            this.clienteService.atualizarDadosCadastro(conn, payload);

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
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

    private Handler verificarReservaDisponivel = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            List<Quarto> reservas = this.hotelService.verificarReservas(conn);

            Utils.sendJsonResponse(Utils.convertObjectToJson(reservas), res);
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce.getReason()), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

    private Handler cancelarReserva = (req, res) -> {
        Reserva payload;

        try {
            payload = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Reserva.class);
        } catch (Exception e) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        Integer reservaId = payload.getReservaId();

        if (Objects.isNull(reservaId)) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            this.clienteService.cancelarReserva(conn, payload);

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
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

    private Handler retornarHistoricoCliente = (req, res) -> {
        Integer clienteId;

        try {
            clienteId = Integer.parseInt(req.getParameter("clienteId"));
        } catch (Exception e) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(clienteId)) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            List<Reserva> reservas = this.clienteService.historicoReservas(conn, clienteId);

            Utils.sendJsonResponse(Utils.convertObjectToJson(reservas), res);
        } catch (Exception e) {
            e.printStackTrace();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce.getReason()), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

}
