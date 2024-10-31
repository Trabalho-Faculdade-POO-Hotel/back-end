package br.com.projeto.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Hotel {
    private int hotelId;
    private String nome;
    private String endereco;
}
