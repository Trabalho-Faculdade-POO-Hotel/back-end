package br.com.projeto.core.service;

import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.ClientException;
import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Reserva;

public class ClienteService<E> {
    private ReservaService<E> reservaService;
    private Class<? extends DAO<Cliente, E>> clienteDAOClass;
    private Class<? extends DAO<Reserva, E>> reservaDAOClass;

    public ClienteService(
            ReservaService<E> reservaService,
            Class<? extends DAO<Cliente, E>> clienteDAOClass,
            Class<? extends DAO<Reserva, E>> reservaDAOClass) {
        this.reservaService = reservaService;
        this.clienteDAOClass = clienteDAOClass;
        this.reservaDAOClass = reservaDAOClass;
    }

    public Cliente obterCliente(E context, Cliente cliente) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);

        List<Cliente> clientes = clienteDAO.get(List.of(
                new DAO.FilterEntry("cliente_id", DAO.FilterEntry.FilterComparator.EQUALS, cliente.getClienteId())));

        if (clientes.size() == 0) {
            return null;
        }

        return clientes.get(0);
    };

    public void cancelarReserva(E context, Reserva reserva) throws Exception {
        this.reservaService.alterarStatus(
                context,
                reserva.getReservaId(),
                Reserva.Status.CANCELADO);
    };

    public void atualizarDadosCadastro(E context, Cliente cliente) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);

        List<Cliente> clientes = clienteDAO.get(List.of(
                new DAO.FilterEntry("cliente_id", DAO.FilterEntry.FilterComparator.EQUALS, cliente.getClienteId())));

        if (clientes.size() == 0) {
            throw new Exception("Cliente não encontrado");
        }

        clienteDAO.update(cliente);
    }

    public List<Reserva> historicoReservas(E context, Integer clienteId) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        if (Objects.isNull(clienteId)) {
            throw new ClientException("Cliente não informado", 400);
        }

        return reservaDAO.get(List.of(
                new DAO.FilterEntry("cliente_id", DAO.FilterEntry.FilterComparator.EQUALS, clienteId)));
    }

    public void fazerReserva(E context, Reserva reserva) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        reservaDAO.create(reserva);
    }

    public void cancelarReserva(E context, Integer reservaId) throws Exception {
        this.reservaService.alterarStatus(context, reservaId, Reserva.Status.CANCELADO);
    }

    public List<Reserva> verificarReservas(E context, Integer clienteId) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        if (Objects.isNull(clienteId)) {
            throw new ClientException("Cliente não informado", 400);
        }

        return reservaDAO.get(List.of(
                new DAO.FilterEntry("cliente_id", DAO.FilterEntry.FilterComparator.EQUALS, clienteId)));
    }
}
