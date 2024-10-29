package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public class Reserva {
    private Integer reservaId;
    private Date dataInicio;
    private Date dataFinal;
    private Status status;
    private Integer clienteId;
    private Integer quartoId;

    public enum Status {
        ATIVO("ATIVO"),
        PENDENTE("PENDENTE"),
        CANCELADO("CANCELADO"),
        NO_SHOW("NO_SHOW"),
        CHECK_IN("CHECK_IN"),
        CHECK_OUT("CHECK_OUT");

        public static Status fromString(String value) {
            switch (value) {
                case "ATIVO":
                    return ATIVO;

                case "PENDENTE":
                    return PENDENTE;

                case "CANCELADO":
                    return CANCELADO;

                case "NO_SHOW":
                    return NO_SHOW;

                case "CHECK_IN":
                    return CHECK_IN;

                case "CHECK_OUT":
                    return CHECK_OUT;

                default:
                    return null;
            }
        }

        private String label;

        private Status(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
    }
}
