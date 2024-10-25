package br.com.projeto.app.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.core.dao.QuartoDAO;
import br.com.projeto.core.entity.Quarto;

public class QuartoDAOImpl implements QuartoDAO {
    private static List<Quarto> quartos = new ArrayList<>();

    private QuartoDAOImpl() {}

    @Override
    public void adicionar(Quarto quarto) {
        quartos.add(quarto);
    }

    @Override
    public Quarto buscarPorNumero(int numero) {
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                return quarto;
            }
        }
        return null; // ou lançar uma exceção
    }

    @Override
    public List<Quarto> listarTodos() {
        return new ArrayList<>(quartos);
    }

    @Override
    public void atualizar(Quarto quarto) {
        Quarto q = buscarPorNumero(quarto.getNumero());
        if (q != null) {
            q.setTipoQuarto(quarto.getTipoQuarto());
            q.setLotacaoQuarto(quarto.getLotacaoQuarto());
            q.setPreco(quarto.getPreco());
            q.setStatusManutencao(quarto.isStatusManutencao());
        }
    }

    @Override
    public void remover(int numero) {
        Quarto quarto = buscarPorNumero(numero);
        if (quarto != null) {
            quartos.remove(quarto);
        }
    }
}