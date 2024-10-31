package br.com.projeto.app.servlet;

import java.sql.Connection;

import br.com.projeto.app.controller.HotelController;
import br.com.projeto.app.controller.QuartoController;
import br.com.projeto.app.impl.dao.ClienteDAOImpl;
import br.com.projeto.app.impl.dao.HotelClienteDAOImpl;
import br.com.projeto.app.impl.dao.HotelDAOImpl;
import br.com.projeto.app.impl.dao.QuartoDAOImpl;
import br.com.projeto.app.impl.dao.ReservaDaoImpl;
import br.com.projeto.core.base.ServletRouter;
import br.com.projeto.core.service.ClienteService;
import br.com.projeto.core.service.HotelService;
import br.com.projeto.core.service.QuartoService;
import br.com.projeto.core.service.ReservaService;

public class Router extends ServletRouter {

    public Router() {
        ReservaService<Connection> reservaService = new ReservaService<>(
                ReservaDaoImpl.class,
                QuartoDAOImpl.class);

        ClienteService<Connection> clienteService = new ClienteService<>(
                reservaService,
                ClienteDAOImpl.class,
                ReservaDaoImpl.class);

        QuartoService<Connection> quartoService = new QuartoService<>(
                HotelDAOImpl.class,
                QuartoDAOImpl.class);

        HotelService<Connection> hotelService = new HotelService<>(
                quartoService,
                clienteService,
                HotelDAOImpl.class,
                HotelClienteDAOImpl.class,
                ReservaDaoImpl.class,
                ClienteDAOImpl.class);

        this.registerController(new HotelController(hotelService));
        this.registerController(new QuartoController(quartoService));
    }

}