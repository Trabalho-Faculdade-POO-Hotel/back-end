package src.models;

import java.util.List;

public interface OperacaoReserva<T> {
    public void fazerReserva(T obj);
    public List<T> verificarReserva();
}
