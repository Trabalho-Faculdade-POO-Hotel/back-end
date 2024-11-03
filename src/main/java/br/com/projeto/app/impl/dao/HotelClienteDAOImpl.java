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
import br.com.projeto.core.entity.HotelCliente;
import br.com.projeto.utils.SQLHelper;

public class HotelClienteDAOImpl extends DAO<HotelCliente, Connection> {

    public HotelClienteDAOImpl(Connection context) {
        super(context);
    }

    @Override
    public List<HotelCliente> get(List<FilterEntry> filter) throws Exception {
        Connection conn = this.getContext();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM hotel_cliente");

        if (Objects.nonNull(filter)) {
            String whereQuery = SQLHelper.buildWhereQuery(filter);

            sqlBuilder.append(whereQuery);
        }

        try (PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString())) {
            if (Objects.nonNull(filter)) {
                SQLHelper.applyValuesToStatement(filter, statement);
            }

            ResultSet result = statement.executeQuery();

            List<HotelCliente> hotelClienteList = new ArrayList<>();
            while (result.next()) {
                hotelClienteList.add(
                        HotelCliente
                                .builder()
                                .hotelId(result.getInt("hotel_id"))
                                .clienteId(result.getInt("cliente_id"))
                                .build());
            }

            return hotelClienteList;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<HotelCliente> get() throws Exception {
        return this.get(null);
    }

    @Override
    public HotelCliente create(HotelCliente entity) throws Exception {
        Connection conn = this.getContext();

        String sql = "INSERT INTO hotel_cliente(hotel_id, cliente_id) VALUES (?, ?)";
        try (PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, entity.getHotelId());
            s.setObject(2, entity.getClienteId());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                return entity;
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    @Override
    public HotelCliente update(HotelCliente updatedEntity) throws Exception {
        throw new UnsupportedOperationException("A entidade 'HotelCliente' não possui operação de atualização");
    }

    @Override
    public void delete(HotelCliente entity) throws Exception {
        Connection conn = this.getContext();

        String sql = "DELETE FROM hotel_cliente WHERE hotel_id = ? AND cliente_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setObject(1, entity.getHotelId());
            s.setObject(2, entity.getClienteId());

            s.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

}
