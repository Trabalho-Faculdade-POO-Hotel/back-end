package br.com.projeto.app.controller;

import java.sql.Connection;
import java.util.Objects;

import br.com.projeto.core.base.ClientException;
import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Quarto.TipoQuarto;
import br.com.projeto.core.service.QuartoService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class QuartoController extends Controller {
    QuartoService<Connection> quartoService;

    public QuartoController(QuartoService<Connection> quartoService) {
        this.quartoService = quartoService;

        this.registerRoute(HttpMethod.GET, "/quarto/listar", this.listarQuartos);
        this.registerRoute(HttpMethod.PUT, "/quarto/atualizar/preco", this.atualizarPreco);
        this.registerRoute(HttpMethod.PUT, "/quarto/atualizar/tipo", this.atualizarTipo);
        this.registerRoute(HttpMethod.PUT, "/quarto/atualizar/manutencao", this.atualizarManutencao);
    }

    private Handler listarQuartos = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            Utils.sendJsonResponse(Utils.convertObjectToJson(this.quartoService.listarTodos(conn)), res);
        } catch (Exception e) {
            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Erro ao listar quartos"), res);
        }
    };

    private Handler atualizarManutencao = (req, res) -> {
        Quarto quarto = null;

        try {
            quarto = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Quarto.class);
        } catch (Exception e) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(quarto) || Objects.isNull(quarto.getNumero())) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        boolean emManutencao = quarto.getEmManutencao();

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            quarto = this.quartoService.buscarPorNumero(conn, quarto.getNumero());
            quarto.setEmManutencao(emManutencao);

            this.quartoService.atualizar(conn, quarto);
        } catch (Exception e) {
            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;
                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce.getReason()), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Erro ao atualizar preço do quarto"), res);

            return;
        }
    };

    private Handler atualizarTipo = (req, res) -> {
        Quarto quarto = null;

        try {
            quarto = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Quarto.class);
        } catch (Exception e) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(quarto) || Objects.isNull(quarto.getNumero())) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        TipoQuarto tipo = quarto.getTipo();

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            quarto = this.quartoService.buscarPorNumero(conn, quarto.getNumero());
            quarto.setTipo(tipo);

            this.quartoService.atualizar(conn, quarto);
        } catch (Exception e) {
            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;
                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce.getReason()), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Erro ao atualizar preço do quarto"), res);

            return;
        }
    };

    private Handler atualizarPreco = (req, res) -> {
        Quarto quarto = null;

        try {
            quarto = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Quarto.class);
        } catch (Exception e) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(quarto) || Objects.isNull(quarto.getNumero())) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        double preco = quarto.getPreco();

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            quarto = this.quartoService.buscarPorNumero(conn, quarto.getNumero());
            quarto.setPreco(preco);

            this.quartoService.atualizar(conn, quarto);
        } catch (Exception e) {
            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;
                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce.getReason()), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Erro ao atualizar preço do quarto"), res);

            return;
        }
    };

}
