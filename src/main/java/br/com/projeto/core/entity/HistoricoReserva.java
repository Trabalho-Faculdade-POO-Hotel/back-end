package br.com.projeto.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class HistoricoReserva {
    Integer historicoId;
    Integer clienteId;
    Integer reservaId;
}