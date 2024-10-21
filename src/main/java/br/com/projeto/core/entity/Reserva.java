package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public class Reserva {
    private Date dtInicial;
    private Date dtFinal;
    private Status status;

    public enum Status {
        ATIVO,
        PENDENTE,
        CANCELADO,
        NO_SHOW,
        CHECK_IN,
        CHECK_OUT
    }
}
