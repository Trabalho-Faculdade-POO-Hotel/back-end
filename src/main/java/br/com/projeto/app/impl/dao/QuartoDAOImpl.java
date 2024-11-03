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
import br.com.projeto.core.entity.Quarto;
import br.com.projeto.core.entity.Quarto.TipoQuarto;
import br.com.projeto.utils.SQLHelper;

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
            String whereQuery = SQLHelper.buildWhereQuery(filter);

            sqlBuilder.append(whereQuery);
        }

        try (PreparedStatement statement = conn.prepareStatement(sqlBuilder.toString())) {
            if (Objects.nonNull(filter)) {
                SQLHelper.applyValuesToStatement(filter, statement);
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

            return List.of();
        }
    }

    @Override
    public List<Quarto> get() {
        return this.get(null);
    }

    @Override
    public Quarto create(Quarto entity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "INSERT INTO quarto(numero, tipo, lotacao, preco, em_manutencao, hotel_id) VALUES (?, ?::tipo_quarto_enum, ?, ?, ?, ?)";
        try (PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, entity.getNumero());
            s.setObject(2, entity.getTipo().toString());
            s.setObject(3, entity.getLotacao());
            s.setObject(4, entity.getPreco());
            s.setObject(5, entity.getEmManutencao());
            s.setObject(6, entity.getHotelId());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = s.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int insertId = generatedKeys.getInt(1);

                    return this.get(List.of(new FilterEntry("quarto_id", FilterComparator.EQUALS, insertId))).get(0);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    @Override
    public Quarto update(Quarto updatedEntity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "UPDATE quarto SET numero = ?, tipo = ?::tipo_quarto_enum, lotacao = ?, preco = ?, em_manutencao = ?, hotel_id = ? WHERE quarto_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, updatedEntity.getNumero());
            s.setObject(2, updatedEntity.getTipo().toString());
            s.setObject(3, updatedEntity.getLotacao());
            s.setObject(4, updatedEntity.getPreco());
            s.setObject(5, updatedEntity.getEmManutencao());
            s.setObject(6, updatedEntity.getHotelId());
            s.setObject(7, updatedEntity.getQuartoId());

            int affectedRows = s.executeUpdate();

            if (affectedRows > 0) {
                return this
                        .get(List
                                .of(new FilterEntry("quarto_id", FilterComparator.EQUALS, updatedEntity.getQuartoId())))
                        .get(0);
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    @Override
    public void delete(Quarto entity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "DELETE FROM quarto WHERE quarto_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, entity.getQuartoId());
            s.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

}