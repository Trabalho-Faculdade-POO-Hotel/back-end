package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private Date dtNascimento;
    private String telefone;
    private String endereco;

}