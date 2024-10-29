package br.com.projeto.core.service;

import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.base.Reservavel;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Reserva;

public class ClienteService<E> implements Reservavel<Reserva, E> {
    private Class<? extends DAO<Cliente, E>> clienteDAOClass;

    public ClienteService(Class<? extends DAO<Cliente, E>> clienteDAOClass) {
        this.clienteDAOClass = clienteDAOClass;
    }

    Cliente obterCliente(E context, Cliente cliente) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);

        List<Cliente> clientes = clienteDAO.get(List.of(
            new DAO.FilterEntry("cliente_id", DAO.FilterEntry.FilterComparator.EQUALS, cliente.getClienteId())
        ));

        if (clientes.size() == 0) {
            return null;
        }

        return clientes.get(0);
    };

    public void cancelarReserva(E context) {

    };

    void atualizarDadosCadastro(E context, Cliente cliente) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);

        List<Cliente> clientes = clienteDAO.get(List.of(
            new DAO.FilterEntry("cliente_id", DAO.FilterEntry.FilterComparator.EQUALS, cliente.getClienteId())
        ));

        if (clientes.size() == 0) {
            throw new Exception("Cliente não encontrado");
        }

        clienteDAO.update(cliente);
    }

    @Override
    public void fazerReserva(E context, Reserva obj) {
        throw new UnsupportedOperationException("Unimplemented method 'fazerReserva'");
    }

    @Override
    public List<Reserva> verificarReservas(E context) {
        throw new UnsupportedOperationException("Unimplemented method 'verificarReservas'");
    };
}
