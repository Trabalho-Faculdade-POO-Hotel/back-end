package br.com.projeto.app.impl;

import br.com.projeto.core.dao.ClienteDAO;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Reserva;

import java.util.Date;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public Cliente obterCliente(Cliente cliente) {
        return cliente;
    }

    @Override
    public void fazerReserva(Reserva obj) {

    }

    @Override
    public List<Reserva> verificarReservas() {
        return List.of();
    }

    @Override
    public void cancelarReserva() {

    }

    @Override
    public void atualizarDadosCadastro(Cliente cliente, String nome, String email, String telefone, String endereco, Date dt_nascimento) {

        try{

            if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
                cliente.setNome(nome);
            }
            if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                cliente.setEmail(email);
            }
            if (cliente.getTelefone()!= null && !cliente.getTelefone().isEmpty()) {
                cliente.setTelefone(telefone);
            }
            if (cliente.getEndereco() != null && !cliente.getEndereco().isEmpty()) {
                cliente.setTelefone(endereco);
            }
            if (cliente.getDtNascimento() != null) {
                cliente.setDtNascimento(dt_nascimento);
            }

        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Cliente não foi cadastrado ou faltam dados no cadastro" +
                    "Entrar em contato com o Hotel");
        }
    }
}
