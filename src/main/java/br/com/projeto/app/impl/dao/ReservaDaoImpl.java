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
import br.com.projeto.core.entity.Reserva;
import br.com.projeto.core.entity.Reserva.Status;
import br.com.projeto.utils.SQLHelper;
import br.com.projeto.utils.Utils;

public class ReservaDaoImpl extends DAO<Reserva, Connection> {

    public ReservaDaoImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Reserva> get(List<FilterEntry> filter) throws SQLException {
        Connection conn = this.getContext();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM reserva");

        if (Objects.nonNull(filter)) {
            String whereQuery = SQLHelper.buildWhereQuery(filter);

            sqlBuilder.append(whereQuery);
        }

        try (PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString())) {
            if (Objects.nonNull(filter)) {
                SQLHelper.applyValuesToStatement(filter, statement);
            }

            ResultSet result = statement.executeQuery();

            List<Reserva> reservaList = new ArrayList<>();
            while (result.next()) {
                reservaList.add(
                        Reserva.builder()
                                .reservaId(result.getInt("reserva_id"))
                                .clienteId((Integer) result.getObject("cliente_id"))
                                .quartoId((Integer) result.getObject("quarto_id"))
                                .dataInicio(result.getDate("data_inicio"))
                                .dataFinal(result.getDate("data_final"))
                                .status(Status.fromString(result.getString("status")))
                                .build());
            }

            return reservaList;
        } catch (SQLException e) {
            e.printStackTrace();

            return List.of();
        }
    }

    @Override
    public List<Reserva> get() throws SQLException {
        return this.get(null);
    }

    @Override
    public Reserva create(Reserva entity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "INSERT INTO reserva(cliente_id, quarto_id, data_inicio, data_final, status) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?::status_enum)";
        try (PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, entity.getClienteId());
            s.setObject(2, entity.getQuartoId());
            s.setObject(3, Utils.convertDateToISOString(entity.getDataInicio(), false));
            s.setObject(4, Utils.convertDateToISOString(entity.getDataFinal(), false));
            s.setObject(5, entity.getStatus().toString());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = s.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertId = generatedKeys.getInt(1);

                    return this.get(List.of(new FilterEntry("reserva_id", FilterComparator.EQUALS, insertId))).get(0);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    @Override
    public Reserva update(Reserva updatedEntity) {
        Connection conn = getContext();

        String sql = "UPDATE reserva SET cliente_id = ?, quarto_id = ?, data_inicio = TO_DATE(?, 'YYYY-MM-DD'), data_final = TO_DATE(?, 'YYYY-MM-DD'), status = ?::status_enum WHERE reserva_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setObject(1, updatedEntity.getClienteId());
            s.setObject(2, updatedEntity.getQuartoId());
            s.setObject(3, Utils.convertDateToISOString(updatedEntity.getDataInicio(), false));
            s.setObject(4, Utils.convertDateToISOString(updatedEntity.getDataFinal(), false));
            s.setObject(5, updatedEntity.getStatus().toString());
            s.setObject(6, updatedEntity.getReservaId());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                return this
                        .get(List.of(
                                new FilterEntry("reserva_id", FilterComparator.EQUALS, updatedEntity.getReservaId())))
                        .get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(Reserva entity) {
        Connection conn = getContext();

        String sql = "DELETE FROM reserva WHERE reserva_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setObject(1, entity.getReservaId());

            s.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
