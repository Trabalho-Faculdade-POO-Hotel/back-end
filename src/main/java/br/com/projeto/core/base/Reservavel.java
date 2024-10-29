package br.com.projeto.core.base;

import java.util.List;

public interface Reservavel<T, E> {
    void fazerReserva(E context, T obj) throws Exception;

    List<T> verificarReservas(E context) throws Exception;
}
