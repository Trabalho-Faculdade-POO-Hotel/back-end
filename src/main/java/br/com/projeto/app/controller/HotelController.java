package br.com.projeto.app.controller;

import java.sql.Connection;

import br.com.projeto.app.impl.dao.HotelDAOImpl;
import br.com.projeto.app.impl.dao.QuartoDAOImpl;
import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.service.HotelService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class HotelController extends Controller {
    HotelService<Connection> service = new HotelService<>(HotelDAOImpl.class, QuartoDAOImpl.class);

    public HotelController() {
        this.registerRoute(HttpMethod.GET, "/hotel", this.getHotelData);
        this.registerRoute(HttpMethod.GET, "/teste", this.teste);
    }

    private Handler teste = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            this.service.adicionarQuarto(conn, null);

            Utils.sendJsonResponse("{ \"message\": \"Successfully tested\" }", res);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

    private Handler getHotelData = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            Hotel hotel = this.service.obterHotel(conn);

            Utils.sendJsonResponse(Utils.convertObjectToJson(hotel), res);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

}
