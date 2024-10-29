package br.com.projeto.app.controller;

import java.sql.Connection;

import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.service.HotelService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class HotelController extends Controller {
    HotelService<Connection> hotelService;

    public HotelController(HotelService<Connection> hotelService) {
        this.hotelService = hotelService;

        this.registerRoute(HttpMethod.GET, "/hotel", this.getHotelData);
    }

    private Handler getHotelData = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            Hotel hotel = this.hotelService.obterHotel(conn);

            Utils.sendJsonResponse(Utils.convertObjectToJson(hotel), res);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

}
