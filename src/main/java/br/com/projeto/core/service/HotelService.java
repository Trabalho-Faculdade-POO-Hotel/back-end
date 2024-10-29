package br.com.projeto.core.service;

import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.base.Reservavel;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.HotelCliente;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;

public class HotelService<E> implements Reservavel<Reserva, E> {
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
            Class<? extends DAO<Reserva, E>> reservaDAOClass) {
        this.quartoService = quartoService;
        this.clienteService = clienteService;
        this.hotelDAOClass = hotelDAOClass;
        this.hotelClienteDAOClass = hotelClienteDAOClass;
        this.reservaDAOClass = reservaDAOClass;
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
            throw new Exception("Quarto já existe");
        }

        this.quartoService.adicionar(context, quarto);
    };

    public void adicionarCliente(E context, Cliente cliente) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);
        DAO<HotelCliente, E> hotelClienteDAO = DAO.createFromClass(this.hotelClienteDAOClass, context);

        Cliente clienteInserido = clienteDAO.create(cliente);

        if (Objects.isNull(clienteInserido)) {
            throw new Exception("Erro ao inserir cliente");
        }

        hotelClienteDAO.create(HotelCliente.builder()
            .clienteId(clienteInserido.getClienteId())
            .hotelId(this.obterHotel(context).getHotelId())
            .build());
    };

    public void bloquearQuarto(E context, Quarto quarto) {
        throw new UnsupportedOperationException("Unimplemented method");
    };

    public List<Cliente> listarClientes(E context) throws Exception {
        DAO<Cliente, E> clienteDAO = DAO.createFromClass(this.clienteDAOClass, context);

        return clienteDAO.get(List.of(
            new DAO.FilterEntry("hotel_id", DAO.FilterEntry.FilterComparator.EQUALS, this.obterHotel(context).getHotelId())
        ));
    };

    @Override
    public void fazerReserva(E context, Reserva obj) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        reservaDAO.create(obj);
    }

    @Override
    public List<Reserva> verificarReservas(E context) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        return reservaDAO.get(List.of(
            new DAO.FilterEntry("hotel_id", DAO.FilterEntry.FilterComparator.EQUALS, this.obterHotel(context).getHotelId())
        ));
    }
}
