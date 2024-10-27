package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Quarto;

public class QuartoDAOImpl extends DAO<Quarto, Connection> {

    public QuartoDAOImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Quarto> get(HashMap<String, Object> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Quarto> get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Quarto create(Quarto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Quarto update(Quarto updatedEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Quarto entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}