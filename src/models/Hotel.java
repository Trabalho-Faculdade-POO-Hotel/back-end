package src.models;

import src.models.*;

import java.util.List;

public class Hotel implements OperacaoReserva<Reserva> {
    private String nome;
    private String endereco;
    private List<Quarto> listaQuartos;
    private List<Reserva> listaReservas;
    private List<Cliente> clientes;

    public Hotel(String nome, String endereco, List<Quarto> listaQuartos, List<Reserva> listaReservas, List<Cliente> listaClientes) {
        this.nome = nome;
        this.endereco = endereco;
        this.listaQuartos = listaQuartos;
        this.listaReservas = listaReservas;
        this.clientes = listaClientes;
    }

    public void adicionarQuarto(Quarto quarto) {
        this.listaQuartos.add(quarto);
    }

    public void adicionarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void bloquearQuarto(Quarto quarto) {
        this.listaQuartos.remove(quarto);
    }

    public List<Cliente> listarClientes() {
        return this.clientes;
    }

    @Override
    public void fazerReserva(Reserva reserva) {
        this.listaReservas.add(reserva);
    }

    @Override
    public List<Reserva> verificarReserva() {
        return this.listaReservas;
    }

}
