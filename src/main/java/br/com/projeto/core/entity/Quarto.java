package br.com.projeto.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Quarto {
    private Integer quartoId;
    private Integer numero;
    private TipoQuarto tipo;
    private Integer lotacao;
    private Double preco;
    private Boolean emManutencao;
    private Integer hotelId;

    public enum TipoQuarto {
        SUITE,
        QUARTO_INDIVIDUAL,
        QUARTO_FAMILIAR,
        QUARTO_EXECUTIVO;

        public static TipoQuarto fromString(String value) {
            switch (value) {
                case "SUITE":
                    return SUITE;

                case "QUARTO_INDIVIDUAL":
                    return QUARTO_INDIVIDUAL;

                case "QUARTO_FAMILIAR":
                    return QUARTO_FAMILIAR;

                case "QUARTO_EXECUTIVO":
                    return QUARTO_EXECUTIVO;

                default:
                    return null;
            }
        }
    }
}
