package br.com.projeto.core.dao;

import br.com.projeto.core.base.Reservavel;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Reserva;

import java.util.Date;
import java.util.List;

public interface ClienteDAO extends Reservavel<Reserva> {

    Cliente obterCliente(Cliente cliente);

    @Override
    void fazerReserva(Reserva obj);

    @Override
    List<Reserva> verificarReservas();

    public void cancelarReserva();

    void atualizarDadosCadastro(Cliente cliente, String nome, String email,
                                String telefone, String endereco, Date dt_nascimento0);
}
