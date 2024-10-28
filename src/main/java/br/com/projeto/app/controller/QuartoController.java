package br.com.projeto.app.controller;

import java.sql.Connection;
import java.util.List;

import br.com.projeto.app.impl.dao.QuartoDAOImpl;
import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.service.QuartoService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class QuartoController extends Controller {
    QuartoService<Connection> quartoService = new QuartoService<>(QuartoDAOImpl.class);

    public QuartoController() {
        this.registerRoute(HttpMethod.GET, "/quarto/list", this.listQuartos);
    }

    Handler listQuartos = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            List<Quarto> quartos = this.quartoService.listarTodos(conn);

            Utils.sendJsonResponse(Utils.convertObjectToJson(quartos), res);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

}
