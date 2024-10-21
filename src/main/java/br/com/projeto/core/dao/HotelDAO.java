package br.com.projeto.core.dao;

import br.com.projeto.core.base.Reservavel;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Reserva;

import java.util.List;

public interface HotelDAO extends Reservavel<Reserva> {
    Hotel obterHotel();
    void adicionarQuarto(Quarto quarto);
    void adicionarCliente(Cliente cliente);
    void bloquearQuarto(Quarto quarto);
    List<Cliente> listarClientes();
}
