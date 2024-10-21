package br.com.projeto.core.base;

import java.util.List;

public interface Reservavel<T> {
    void fazerReserva(T obj);
    List<T> verificarReservas();
}
