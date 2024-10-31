package br.com.projeto.app.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.service.HotelService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class HotelController extends Controller {
    HotelService<Connection> hotelService;

    public HotelController(HotelService<Connection> hotelService) {
        this.hotelService = hotelService;

        this.registerRoute(HttpMethod.GET, "/hotel", this.retornarDadosHotel);
        this.registerRoute(HttpMethod.POST, "/hotel/cliente/adicionar", this.adicionarCliente);
    }

    private Handler retornarDadosHotel = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            Hotel hotel = this.hotelService.obterHotel(conn);

            Utils.sendJsonResponse(Utils.convertObjectToJson(hotel), res);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

    private Handler adicionarCliente = (req, res) -> {
        Connection conn = DBConnectionPool.getDataSource().getConnection();
        conn.setAutoCommit(false);

        try {
            String clienteJson = Utils.getJsonFromRequestBody(req);

            Cliente cliente;
            try {
                cliente = Utils.getEntityFromJson(clienteJson, Cliente.class);
            } catch (Exception e) {
                e.printStackTrace();

                res.setStatus(400);
                Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

                conn.rollback();

                return;
            }

            this.hotelService.adicionarCliente(conn, cliente);

            conn.commit();

            res.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();

            conn.rollback();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        } finally {
            conn.close();
        }
    };

}
