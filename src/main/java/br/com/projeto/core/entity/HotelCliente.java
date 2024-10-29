package br.com.projeto.core.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class HotelCliente {
    Integer hotelId;
    Integer clienteId;
}
