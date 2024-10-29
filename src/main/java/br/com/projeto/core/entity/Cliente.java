package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public class Cliente {
    private Integer clienteId;
    private String nome;
    private String email;
    private Date dataNascimento;
    private String telefone;
    private String endereco;

}