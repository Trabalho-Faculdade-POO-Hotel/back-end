package br.com.projeto.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class HotelCliente {
    Integer hotelId;
    Integer clienteId;
}
