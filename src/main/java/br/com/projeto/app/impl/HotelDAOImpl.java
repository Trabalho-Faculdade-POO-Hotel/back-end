package br.com.projeto.app.impl;

import br.com.projeto.core.dao.HotelDAO;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;
import br.com.projeto.utils.DBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HotelDAOImpl implements HotelDAO {

    @Override
    public Hotel obterHotel() {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection()) {
            String sql = "SELECT * FROM hotel LIMIT 1";
            PreparedStatement s = conn.prepareStatement(sql);

            ResultSet rs = s.executeQuery();

            if (rs.next()) {
                return Hotel
                        .builder()
                        .hotelId(rs.getInt("hotel_id"))
                        .nome(rs.getString("nome"))
                        .endereco(rs.getString("endereco"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void adicionarQuarto(Quarto quarto) {

    }

    @Override
    public void adicionarCliente(Cliente cliente) {

    }

    @Override
    public void bloquearQuarto(Quarto quarto) {

    }

    @Override
    public List<Cliente> listarClientes() {
        return List.of();
    }

    @Override
    public void fazerReserva(Reserva obj) {

    }

    @Override
    public List<Reserva> verificarReservas() {
        return List.of();
    }
}
