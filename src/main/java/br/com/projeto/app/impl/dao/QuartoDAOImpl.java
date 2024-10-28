package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Quarto.TipoQuarto;

public class QuartoDAOImpl extends DAO<Quarto, Connection> {

    public QuartoDAOImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Quarto> get(List<FilterEntry> filter) {
        Connection conn = this.getContext();

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM quarto");

        if (Objects.nonNull(filter)) {
            sqlBuilder.append(" WHERE ");

            String whereQuery = String.join(
                    " AND ",
                    filter.stream().map(f -> f.buildQuery()).toList());

            sqlBuilder.append(whereQuery);
        }

        try (PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString())) {
            if (Objects.nonNull(filter)) {
                int index = 1;

                for (Object value : filter.stream().map(f -> f.getValue()).toList()) {
                    statement.setObject(index, value);

                    index++;
                }
            }

            ResultSet result = statement.executeQuery();

            List<Quarto> quartoList = new ArrayList<>();
            while (result.next()) {
                quartoList.add(Quarto
                        .builder()
                        .quartoId(result.getInt("quarto_id"))
                        .numero(result.getInt("numero"))
                        .tipo(TipoQuarto.fromString(result.getString("tipo")))
                        .lotacao(result.getInt("lotacao"))
                        .preco(result.getDouble("preco"))
                        .emManutencao(result.getBoolean("em_manutencao"))
                        .hotelId((Integer) result.getObject("hotel_id")).build());
            }

            return quartoList;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<Quarto> get() {
        return this.get(null);
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