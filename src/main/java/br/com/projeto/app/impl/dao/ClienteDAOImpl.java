package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.base.DAO.FilterEntry.FilterComparator;
import br.com.projeto.core.entity.Cliente;
import br.com.projeto.utils.SQLHelper;

public class ClienteDAOImpl extends DAO<Cliente, Connection> {

    public ClienteDAOImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Cliente> get(List<FilterEntry> filter) throws SQLException {
        Connection conn = this.getContext();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM cliente");

        if (Objects.nonNull(filter)) {
            String whereQuery = SQLHelper.buildWhereQuery(filter);

            sqlBuilder.append(whereQuery);
        }

        try (PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString())) {
            if (Objects.nonNull(filter)) {
                SQLHelper.applyValuesToStatement(filter, statement);
            }

            ResultSet result = statement.executeQuery();

            List<Cliente> clienteList = new ArrayList<>();
            while (result.next()) {
                clienteList.add(
                        Cliente.builder()
                                .clienteId((Integer) result.getObject("cliente_id"))
                                .nome(result.getString("nome"))
                                .email(result.getString("email"))
                                .dataNascimento(result.getDate("data_nascimento"))
                                .telefone(result.getString("telefone"))
                                .endereco(result.getString("endereco"))
                                .build());
            }

            return clienteList;
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Cliente> get() throws SQLException {
        return this.get(null);
    }

    @Override
    public Cliente create(Cliente entity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "INSERT INTO cliente(nome, email, data_nascimento, telefone, endereco) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, entity.getNome());
            s.setObject(2, entity.getEmail());
            s.setObject(3, entity.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            s.setObject(4, entity.getTelefone());
            s.setObject(5, entity.getEndereco());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = s.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertId = generatedKeys.getInt(1);

                    return this.get(List.of(new FilterEntry("cliente_id", FilterComparator.EQUALS, insertId))).get(0);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    @Override
    public Cliente update(Cliente updatedEntity) {
        Connection conn = getContext();

        String sql = "UPDATE cliente SET nome = ?, email = ?, data_nascimento = ?, telefone = ?, endereco = ? WHERE cliente_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setObject(1, updatedEntity.getNome());
            s.setObject(2, updatedEntity.getEmail());
            s.setObject(3, updatedEntity.getDataNascimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            s.setObject(4, updatedEntity.getTelefone());
            s.setObject(5, updatedEntity.getEndereco());
            s.setObject(6, updatedEntity.getClienteId());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                return this
                        .get(List.of(
                                new FilterEntry("cliente_id", FilterComparator.EQUALS, updatedEntity.getClienteId())))
                        .get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Cliente entity) {
        Connection conn = getContext();

        String sql = "DELETE FROM cliente WHERE cliente_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setObject(1, entity.getClienteId());

            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
