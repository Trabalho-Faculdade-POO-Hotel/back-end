package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.base.DAO.FilterEntry.FilterComparator;
import br.com.projeto.core.entity.HistoricoReserva;
import br.com.projeto.utils.SQLHelper;

public class HistoricoReservaDAOImpl extends DAO<HistoricoReserva, Connection> {

    public HistoricoReservaDAOImpl(Connection context) {
        super(context);
    }

    @Override
    public List<HistoricoReserva> get(List<FilterEntry> filter) throws Exception {
        Connection conn = this.getContext();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM historico_reserva");

        if (Objects.nonNull(filter)) {
            String whereQuery = SQLHelper.buildWhereQuery(filter);

            sqlBuilder.append(whereQuery);
        }

        try (PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString())) {
            if (Objects.nonNull(filter)) {
                SQLHelper.applyValuesToStatement(filter, statement);
            }

            ResultSet result = statement.executeQuery();

            List<HistoricoReserva> historicoReservaList = new ArrayList<>();
            while (result.next()) {
                historicoReservaList.add(
                        HistoricoReserva
                                .builder()
                                .clienteId(result.getInt("cliente_id"))
                                .reservaId(result.getInt("reserva_id"))
                                .build());
            }

            return historicoReservaList;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<HistoricoReserva> get() throws Exception {
        return this.get(null);
    }

    @Override
    public HistoricoReserva create(HistoricoReserva entity) throws Exception {
        Connection conn = this.getContext();

        String sql = "INSERT INTO historico_reserva(cliente_id, reserva_id) VALUES (?, ?)";
        try (PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, entity.getClienteId());
            s.setObject(2, entity.getReservaId());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = s.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertId = generatedKeys.getInt(1);

                    return this.get(List.of(new FilterEntry("historico_id", FilterComparator.EQUALS, insertId))).get(0);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    @Override
    public HistoricoReserva update(HistoricoReserva updatedEntity) throws Exception {
        Connection conn = this.getContext();

        String sql = "UPDATE historico_reserva SET cliente_id = ?, reserva_id = ? WHERE historico_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, updatedEntity.getClienteId());
            s.setObject(2, updatedEntity.getReservaId());
            s.setObject(3, updatedEntity.getHistoricoId());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                return this
                        .get(List
                                .of(new FilterEntry("cliente_id", FilterComparator.EQUALS,
                                        updatedEntity.getClienteId())))
                        .get(0);
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    @Override
    public void delete(HistoricoReserva entity) throws Exception {
        Connection conn = this.getContext();

        String sql = "DELETE FROM historico_reserva WHERE historico_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setObject(1, entity.getHistoricoId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

}
