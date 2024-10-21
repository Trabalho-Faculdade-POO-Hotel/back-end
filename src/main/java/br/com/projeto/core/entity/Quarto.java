package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Quarto {
    private int numero;
    private TipoQuarto tipoQuarto;
    private int lotacaoQuarto;
    private double preco;
    private boolean statusManutencao;

    public enum TipoQuarto {
        SUITE,
        QUARTO_INDIVIDUAL,
        QUARTO_FAMILIAR,
        QUARTO_EXECUTIVO,
    }
}
