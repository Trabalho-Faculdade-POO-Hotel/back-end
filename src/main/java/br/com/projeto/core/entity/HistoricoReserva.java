package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class HistoricoReserva {
    Integer historicoId;
    Integer clienteId;
    Integer reservaId;
}