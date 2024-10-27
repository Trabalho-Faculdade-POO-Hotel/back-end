package br.com.projeto.core.service;

import java.util.List;

import br.com.projeto.core.entity.Quarto;

public interface QuartoService<E> {
    void adicionar(E context, Quarto quarto);

    Quarto buscarPorNumero(E context, int numero);

    List<Quarto> listarTodos(E context);

    void atualizar(E context, Quarto quarto);

    void remover(E context, int numero);
}
