package br.com.projeto.app.impl;

import br.com.projeto.core.dao.ClienteDAO;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Reserva;

import java.util.Date;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public Cliente obterCliente(Cliente cliente) {
        return cliente;
    }

    @Override
    public void fazerReserva(Reserva obj) {

    }

    @Override
    public List<Reserva> verificarReservas() {
        return List.of();
    }

    @Override
    public void cancelarReserva() {

    }

    @Override
    public void atualizarDadosCadastro(Cliente cliente, String nome, String email, String telefone, String endereco, Date dt_nascimento) {

    }
}
