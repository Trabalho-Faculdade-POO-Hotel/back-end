package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Hotel;

public class HotelDAOImpl extends DAO<Hotel, Connection> {

    public HotelDAOImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Hotel> get(List<FilterEntry> filter) throws SQLException {
        return this.get();
    }

    @Override
    public List<Hotel> get() throws SQLException {
        Connection conn = this.getContext();

        String sql = "SELECT * FROM hotel LIMIT 1";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            ResultSet rs = s.executeQuery();

            if (rs.next()) {
                return List.of(Hotel
                        .builder()
                        .hotelId(rs.getInt("hotel_id"))
                        .nome(rs.getString("nome"))
                        .endereco(rs.getString("endereco"))
                        .build());
            }
        } catch (SQLException e) {
            throw e;
        }

        return List.of();
    }

    @Override
    public Hotel create(Hotel entity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "INSERT INTO hotel(nome, endereco) VALUES (?, ?)";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, entity.getNome());
            s.setString(2, entity.getEndereco());
            s.executeUpdate();

            List<Hotel> hotelList = this.get();

            return hotelList.get(0);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Hotel update(Hotel updatedEntity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "UPDATE hotel SET nome = ?, endereco = ? WHERE hotel_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, updatedEntity.getNome());
            s.setString(2, updatedEntity.getEndereco());
            s.setInt(3, updatedEntity.getHotelId());
            s.executeUpdate();

            List<Hotel> hotelList = this.get();

            return hotelList.get(0);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void delete(Hotel entity) throws SQLException {
        Connection conn = this.getContext();

        String sql = "DELETE FROM hotel WHERE hotel_id = ?";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setInt(1, entity.getHotelId());
            s.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

}
