package br.com.projeto.core.service;

import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;

public class ReservaService<E> {
    Class<? extends DAO<Reserva, E>> reservaDAOClass;
    Class<? extends DAO<Quarto, E>> quartoDAOClass;

    public ReservaService(
            Class<? extends DAO<Reserva, E>> reservaDAOClass,
            Class<? extends DAO<Quarto, E>> quartoDAOClass) {
        this.reservaDAOClass = reservaDAOClass;
        this.quartoDAOClass = quartoDAOClass;
    }

    public void alterarStatus(E context, int reservaId, Reserva.Status status) throws Exception {
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        List<Reserva> reservas = reservaDAO.get(List.of(
                new DAO.FilterEntry("reserva_id", DAO.FilterEntry.FilterComparator.EQUALS, reservaId)));

        if (reservas.size() == 0) {
            throw new Exception("Reserva não encontrada");
        }

        Reserva reserva = reservas.get(0);
        reservaDAO.update(reserva.toBuilder().status(status).build());
    }

    public void alterarQuarto(E context, int reservaId, int quartoId) throws Exception {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);
        DAO<Reserva, E> reservaDAO = DAO.createFromClass(this.reservaDAOClass, context);

        List<Quarto> quartos = quartoDAO.get(List.of(
                new DAO.FilterEntry("quarto_id", DAO.FilterEntry.FilterComparator.EQUALS, quartoId)));

        if (quartos.size() == 0) {
            throw new Exception("Quarto não encontrado");
        }

        List<Reserva> reservas = reservaDAO.get(List.of(
                new DAO.FilterEntry("reserva_id", DAO.FilterEntry.FilterComparator.EQUALS, reservaId)));

        if (reservas.size() == 0) {
            throw new Exception("Reserva não encontrada");
        }

        Reserva reserva = reservas.get(0);
        reservaDAO.update(reserva.toBuilder().quartoId(quartoId).build());
    }

}
