package br.com.projeto.app.servlet;

import br.com.projeto.app.controller.HotelController;
import br.com.projeto.app.controller.QuartoController;
import br.com.projeto.core.base.ServletRouter;

public class Router extends ServletRouter {

    public Router() {
        this.registerController(new HotelController());
        this.registerController(new QuartoController());
    }

}