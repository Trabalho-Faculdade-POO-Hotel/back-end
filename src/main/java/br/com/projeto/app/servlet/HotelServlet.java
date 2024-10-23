package br.com.projeto.app.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projeto.core.entity.Cliente;
import br.com.projeto.utils.DBConnectionPool;
import br.com.projeto.utils.Utils;

public class HotelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getPathInfo());

        Utils.sendJsonResponse("{\"message\": \"Olá Cauã !\"}", resp);
    }

}