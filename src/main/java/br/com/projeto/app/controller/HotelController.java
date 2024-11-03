package br.com.projeto.app.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.ClientException;
import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;
import br.com.projeto.core.service.HotelService;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class HotelController extends Controller {
    HotelService<Connection> hotelService;

    public HotelController(HotelService<Connection> hotelService) {
        this.hotelService = hotelService;

        this.registerRoute(HttpMethod.GET, "/hotel", this.retornarDadosHotel);
        this.registerRoute(HttpMethod.POST, "/hotel/cliente/adicionar", this.adicionarCliente);
        this.registerRoute(HttpMethod.GET, "/hotel/cliente/listar", this.listarClientes);
        this.registerRoute(HttpMethod.POST, "/hotel/quarto/adicionar", this.adicionarQuarto);
        this.registerRoute(HttpMethod.PUT, "/hotel/quarto/bloquear", this.bloquearQuarto);
        this.registerRoute(HttpMethod.GET, "/hotel/quarto/verificar", this.verificarQuarto);
        this.registerRoute(HttpMethod.POST, "/hotel/reserva/adicionar", this.adicionarReserva);
        this.registerRoute(HttpMethod.GET, "/hotel/quarto/disponiveis", this.verificarQuartosDisponiveis);
    }

    private Handler verificarQuartosDisponiveis = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            List<Quarto> quartosParaReservas = this.hotelService.verificarReservas(conn);

            Utils.sendJsonResponse(Utils.convertObjectToJson(quartosParaReservas), res);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

    private Handler adicionarReserva = (req, res) -> {
        Reserva reserva;

        try {
            String reservaJson = Utils.getJsonFromRequestBody(req);
            reserva = Utils.getEntityFromJson(reservaJson, Reserva.class);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(reserva)) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            try {
                hotelService.fazerReserva(conn, reserva);

                conn.commit();

                res.setStatus(200);
            } catch (Exception e) {
                e.printStackTrace();

                conn.rollback();

                if (e instanceof ClientException) {
                    ClientException ce = (ClientException) e;

                    res.setStatus(ce.getResponseCode());
                    Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce), res);

                    return;
                }

                res.setStatus(500);
                Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
            }
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

    private Handler listarClientes = (req, res) -> {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            List<Cliente> clientes = this.hotelService.listarClientes(conn);

            Utils.sendJsonResponse(Utils.convertObjectToJson(clientes), res);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        }
    };

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
        Cliente cliente;
        String clienteJson = Utils.getJsonFromRequestBody(req);

        try {
            cliente = Utils.getEntityFromJson(clienteJson, Cliente.class);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(cliente)) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        Connection conn = DBConnectionPool.getDataSource().getConnection();
        conn.setAutoCommit(false);
        try {
            this.hotelService.adicionarCliente(conn, cliente);

            conn.commit();

            res.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();

            conn.rollback();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        } finally {
            conn.close();
        }
    };

    private Handler adicionarQuarto = (req, res) -> {
        Quarto quarto = null;

        try {
            quarto = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Quarto.class);
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(quarto)) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        Connection conn = DBConnectionPool.getDataSource().getConnection();
        conn.setAutoCommit(false);

        try {
            this.hotelService.adicionarQuarto(conn, quarto);

            conn.commit();

            res.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();

            conn.rollback();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        } finally {
            conn.close();
        }
    };

    private Handler bloquearQuarto = (req, res) -> {
        Integer numero = null;

        try {
            Quarto dadosPayload = Utils.getEntityFromJson(Utils.getJsonFromRequestBody(req), Quarto.class);

            numero = dadosPayload.getNumero();
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        if (Objects.isNull(numero)) {
            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("número do quarto é obrigatório"), res);

            return;
        }

        Connection conn = DBConnectionPool.getDataSource().getConnection();
        conn.setAutoCommit(false);

        try {
            this.hotelService.bloquearQuarto(conn, numero);

            conn.commit();

            res.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();

            conn.rollback();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        } finally {
            conn.close();
        }
    };

    private Handler verificarQuarto = (req, res) -> {
        Integer numero = null;

        try {
            numero = Integer.parseInt(req.getParameter("numero"));
        } catch (Exception e) {
            e.printStackTrace();

            res.setStatus(400);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Dados inválidos"), res);

            return;
        }

        Connection conn = DBConnectionPool.getDataSource().getConnection();
        conn.setAutoCommit(false);

        try {
            boolean quartoLivre = this.hotelService.verificarQuarto(conn, numero);

            conn.commit();

            res.setStatus(200);
            Utils.sendJsonResponse(quartoLivre ? "true" : "false", res);
        } catch (Exception e) {
            e.printStackTrace();

            conn.rollback();

            if (e instanceof ClientException) {
                ClientException ce = (ClientException) e;

                res.setStatus(ce.getResponseCode());
                Utils.sendJsonResponse(Utils.createErrorJsonPayload(ce), res);

                return;
            }

            res.setStatus(500);
            Utils.sendJsonResponse(Utils.createErrorJsonPayload("Ocorreu um erro inesperado"), res);
        } finally {
            conn.close();
        }
    };

}
