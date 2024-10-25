package br.com.projeto.core.dao;

import java.util.List;

import br.com.projeto.core.entity.Quarto;

public interface QuartoDAO {
    void adicionar(Quarto quarto);
    Quarto buscarPorNumero(int numero);
    List<Quarto> listarTodos();
    void atualizar(Quarto quarto);
    void remover(int numero);
}