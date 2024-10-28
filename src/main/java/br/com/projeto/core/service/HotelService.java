package br.com.projeto.core.service;

import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.base.Reservavel;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;

public class HotelService<E> implements Reservavel<Reserva, E> {
    Class<? extends DAO<Hotel, E>> hotelDAOClass;
    Class<? extends DAO<Quarto, E>> quartoDAOClass;

    public HotelService(
            Class<? extends DAO<Hotel, E>> hotelDAOClass,
            Class<? extends DAO<Quarto, E>> quartoDAOClass) {
        this.hotelDAOClass = hotelDAOClass;
        this.quartoDAOClass = quartoDAOClass;
    }

    public Hotel obterHotel(E context) {
        DAO<Hotel, E> hotelDAO = DAO.createFromClass(this.hotelDAOClass, context);

        return hotelDAO.get().get(0);
    };

    public void adicionarQuarto(E context, Quarto quarto) {

    };

    public void adicionarCliente(E context, Cliente cliente) {
        throw new UnsupportedOperationException("Unimplemented method");
    };

    public void bloquearQuarto(E context, Quarto quarto) {
        throw new UnsupportedOperationException("Unimplemented method");
    };

    public List<Cliente> listarClientes(E context) {
        throw new UnsupportedOperationException("Unimplemented method");
    };

    @Override
    public void fazerReserva(E context, Reserva obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fazerReserva'");
    }

    @Override
    public List<Reserva> verificarReservas(E context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verificarReservas'");
    }
}
