package br.com.projeto.core.entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Cliente {
    private Integer clienteId;
    private String nome;
    private String email;
    private Date dataNascimento;
    private String telefone;
    private String endereco;

}