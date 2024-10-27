package br.com.projeto.core.service;

import java.util.Date;

import br.com.projeto.core.base.Reservavel;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Reserva;

public interface ClienteService<E> extends Reservavel<Reserva, E> {
    Cliente obterCliente(E context, Cliente cliente);

    public void cancelarReserva(E context);

    void atualizarDadosCadastro(E context, Cliente cliente, String nome, String email,
            String telefone, String endereco, Date dtNascimento);
}
