package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Cliente;

public class ClienteDAOImpl extends DAO<Cliente, Connection> {

    public ClienteDAOImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Cliente> get(List<FilterEntry> filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Cliente> get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Cliente create(Cliente entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Cliente update(Cliente updatedEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Cliente entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
