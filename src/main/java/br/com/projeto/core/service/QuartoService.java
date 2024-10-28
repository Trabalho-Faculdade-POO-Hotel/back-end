package br.com.projeto.core.service;

import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.base.DAO.FilterEntry.FilterComparator;
import br.com.projeto.core.entity.Quarto;

public class QuartoService<E> {
    Class<? extends DAO<Quarto, E>> quartoDAOClass;

    public QuartoService(Class<? extends DAO<Quarto, E>> quartoDAOClass) {
        this.quartoDAOClass = quartoDAOClass;
    }

    public void adicionar(E context, Quarto quarto) {

    };

    public Quarto buscarPorNumero(E context, int numero) {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);

        return quartoDAO.get(
                List.of(new DAO.FilterEntry("numero", FilterComparator.EQUALS, numero)))
                .get(0);
    };

    public List<Quarto> listarTodos(E context) {
        DAO<Quarto, E> quartoDAO = DAO.createFromClass(this.quartoDAOClass, context);

        return quartoDAO.get();
    };

    public void atualizar(E context, Quarto quarto) {

    };

    public void remover(E context, int numero) {

    };
}
