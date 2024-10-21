package br.com.projeto.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.servlet.JsonServlet;

public class HelloWorldServlet extends JsonServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("Olá Mundo !");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}