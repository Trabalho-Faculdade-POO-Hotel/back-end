package br.com.projeto.app.impl;

import br.com.projeto.core.dao.HotelDAO;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;

import java.util.List;

public class HotelDAOImpl implements HotelDAO {
    @Override
    public Hotel obterHotel() {
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
