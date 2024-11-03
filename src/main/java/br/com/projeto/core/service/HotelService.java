package br.com.projeto.core.service;

import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.ClientException;
import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.HotelCliente;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;

public class HotelService<E> {
    Class<? extends DAO<Hotel, E>> hotelDAOClass;
    Class<? extends DAO<Cliente, E>> clienteDAOClass;
    Class<? extends DAO<HotelCliente, E>> hotelClienteDAOClass;
    Class<? extends DAO<Reserva, E>> reservaDAOClass;
    QuartoService<E> quartoService;
    ClienteService<E> clienteService;

    public HotelService(
            QuartoService<E> quartoService,
            ClienteService<E> clienteService,
            Class<? extends DAO<Hotel, E>> hotelDAOClass,
            Class<? extends DAO<HotelCliente, E>> hotelClienteDAOClass,
            Class<? extends DAO<Reserva, E>> reservaDAOClass,
            Class<? extends DAO<Cliente, E>> clienteDAOClass) {
        this.quartoService = quartoService;
        this.clienteService = clienteService;
        this.hotelDAOClass = hotelDAOClass;
        this.hotelClienteDAOClass = hotelClienteDAOClass;
        this.reservaDAOClass = reservaDAOClass;
        this.clienteDAOClass = clienteDAOClass;
    }

    public Hotel obterHotel(E context) throws Exception {
        DAO<Hotel, E> hotelDAO = DAO.createFromClass(this.hotelDAOClass, context);

        List<Hotel> hoteis = hotelDAO.get();

        if (hoteis.size() == 0) {
            return null;
        }

        return hotelDAO.get().get(0);
    };

    public void adicionarQuarto(E context, Quarto quarto) throws Exception {
        Quarto quartoExistente = this.quartoService.buscarPorNumero(context, quarto.getNumero());

        if (Objects.nonNull(quartoExistente)) {
            throw new ClientException("Quarto já existe", 400);
        }

        this.quartoService.adicionar(context, quarto);
    };

    public void adicionarCliente(E context, Cliente cliente) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);
        DAO<HotelCliente, E> hotelClienteDAO = DAO.createFromClass(this.hotelClienteDAOClass, context);

        if (Objects.isNull(cliente.getEmail()) || cliente.getEmail().isEmpty()) {
            throw new ClientException("Email é obrigatório", 400);
        }

        Cliente clienteExistente = clienteDAO.get(List.of(
                new DAO.FilterEntry("email", DAO.FilterEntry.FilterComparator.EQUALS, cliente.getEmail())))
                .stream()
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(clienteExistente)) {
            throw new ClientException("Cliente existente", 400);
        }

        Cliente clienteInserido = clienteDAO.create(cliente);

        if (Objects.isNull(clienteInserido)) {
            throw new ClientException("Erro ao inserir cliente", 400);
        }

        hotelClienteDAO.create(HotelCliente.builder()
                .clienteId(clienteInserido.getClienteId())
                .hotelId(this.obterHotel(context).getHotelId())
                .build());
    };

    public void bloquearQuarto(E context, Integer numero) throws Exception {
        if (Objects.isNull(numero)) {
            throw new ClientException("HotelId é obrigatório", 400);
        }

        Quarto quartoExistente = this.quartoService.buscarPorNumero(context, numero);

        if (Objects.isNull(quartoExistente)) {
            throw new ClientException("Quarto não existe", 400);
        }

        quartoExistente.setEmManutencao(true);

        this.quartoService.atualizar(context, quartoExistente);
    };

    public List<Cliente> listarClientes(E context) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);

        return clienteDAO.get();
    };

    public boolean verificarQuarto(E context, Integer numero) throws Exception {
        Quarto quarto = this.quartoService.buscarPorNumero(context, numero);

        if (Objects.isNull(quarto)) {
            throw new ClientException("Quarto não existe", 400);
        }

        if (quarto.getEmManutencao()) {
            return false;
        }

        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);
        List<Reserva> reservas = reservaDAO.get(List.of(
                new DAO.FilterEntry("quarto_id", DAO.FilterEntry.FilterComparator.EQUALS, quarto.getQuartoId()),
                new DAO.FilterEntry("status",
                        List.of(Reserva.Status.ATIVO.toString(),
                                Reserva.Status.CHECK_IN.toString()),
                        "status_enum")));

        return reservas.isEmpty();
    }

    public void fazerReserva(E context, Reserva reserva) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        Integer clienteId = reserva.getClienteId();

        if (Objects.isNull(clienteId)) {
            throw new ClientException("Cliente não informado", 400);
        }

        List<Reserva> reservasCliente = this.clienteService.verificarReservas(context, clienteId);

        if (reservasCliente.stream().anyMatch(r -> r.getStatus().equals(Reserva.Status.ATIVO))) {
            throw new ClientException("Cliente já possui reserva ativa", 400);
        }

        reservaDAO.create(reserva);
    }

    public List<Quarto> verificarReservas(E context) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        List<Reserva> reservasAtivas = reservaDAO.get(List.of(
                new DAO.FilterEntry(
                        "status",
                        List.of(Reserva.Status.ATIVO.toString(), Reserva.Status.CHECK_IN.toString()),
                        "status_enum")));

        List<Quarto> quartos = this.quartoService.listarTodos(context);
        List<Quarto> quartosDisponiveis = quartos.stream()
                .filter(q -> reservasAtivas.stream().noneMatch(r -> r.getQuartoId() == q.getQuartoId()))
                .toList();

        return quartosDisponiveis;
    }

}
