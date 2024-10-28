package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Reserva;

public class ReservaDaoImpl extends DAO<Reserva, Connection> {

    public ReservaDaoImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Reserva> get(List<FilterEntry> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Reserva> get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Reserva create(Reserva entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Reserva update(Reserva updatedEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Reserva entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
