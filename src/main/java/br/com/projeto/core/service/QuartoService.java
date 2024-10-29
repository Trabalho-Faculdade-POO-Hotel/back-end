package br.com.projeto.core.service;

import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.base.DAO.FilterEntry;
import br.com.projeto.core.base.DAO.FilterEntry.FilterComparator;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.Quarto;

public class QuartoService<E> {
    Class<? extends DAO<Quarto, E>> quartoDAOClass;
    Class<? extends DAO<Hotel, E>> hotelDAOClass;

    public QuartoService(Class<? extends DAO<Hotel, E>> hotelDAOClass, Class<? extends DAO<Quarto, E>> quartoDAOClass) {
        this.quartoDAOClass = quartoDAOClass;
        this.hotelDAOClass = hotelDAOClass;
    }

    public void adicionar(E context, Quarto quarto) throws Exception {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);

        quartoDAO.create(quarto);
    };

    public Quarto buscarPorNumero(E context, int numero) throws Exception {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);
        DAO<Hotel, E> hotelDAO = DAO.createFromClass(this.hotelDAOClass, context);

        List<Hotel> hotel = hotelDAO.get();

        if (hotel.size() == 0) {
            throw new Exception("Hotel não cadastrado");
        }

        List<Quarto> quartos = quartoDAO.get(
            List.of(
                new DAO.FilterEntry("hotel_id", FilterEntry.FilterComparator.EQUALS, hotel.get(0).getHotelId()),
                new DAO.FilterEntry("numero", FilterComparator.EQUALS, numero)
            ));

        if (quartos.size() == 0) {
            return null;
        }

        return quartos.get(0);
    };

    public List<Quarto> listarTodos(E context) throws Exception {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);

        return quartoDAO.get();
    };

    public void atualizar(E context, Quarto quarto) throws Exception {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);

        quartoDAO.update(quarto);
    };

    public void remover(E context, int numero) throws Exception {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);

        List<Quarto> quartos = quartoDAO.get(List.of(new FilterEntry("numero", FilterComparator.EQUALS, numero)));

        if (quartos.size() > 0) {
            quartoDAO.delete(Quarto.builder().quartoId(quartos.get(0).getQuartoId()).build());
        }
    };
}
