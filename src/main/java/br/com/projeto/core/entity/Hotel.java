package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Hotel {
    private int hotelId;
    private String nome;
    private String endereco;
}
