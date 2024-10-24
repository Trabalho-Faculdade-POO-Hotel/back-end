package src.models;

import src.models.OperacaoReserva;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente implements OperacaoReserva {
    private String nome;
    private String cpf;
    private String email;
    private Date dt_nascimento;
    private String telefone;
    private String endereco;
    private List<String> observacoes;


    Cliente(){}

    public Cliente(String nome, String cpf, String email, Date dt_nascimento, String telefone, String endereco, List<String> observacoes) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dt_nascimento = dt_nascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.observacoes = observacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(Date dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }



    public List<Object> historicoCliente(){
        List<Object> historico = new ArrayList<>();
        return historico;
    }

    public void cancelarReserva(){

    }

    @Override
    public void fazerReserva(Object obj) {

    }

    @Override
    public List<Object> verificarReserva() {
        return List.of();
    }

    public void atualizarDados(String nome, String email, String telefone, String endereco, Date dt_nascimento, List<String> observacoes) {
        if (nome != null && !nome.isEmpty()) {
            this.nome = nome;
        }
        if (email != null && !email.isEmpty()) {
            this.email = email;
        }
        if (telefone != null && !telefone.isEmpty()) {
            this.telefone = telefone;
        }
        if (endereco != null && !endereco.isEmpty()) {
            this.endereco = endereco;
        }
        if (dt_nascimento != null) {
            this.dt_nascimento = dt_nascimento;
        }
        if (observacoes != null) {
            this.observacoes = observacoes;
        }
    }
}
