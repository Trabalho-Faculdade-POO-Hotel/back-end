package br.com.projeto.app.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import br.com.projeto.core.base.DAO;
import br.com.projeto.core.entity.Hotel;

public class HotelDAOImpl extends DAO<Hotel, Connection> {

    public HotelDAOImpl(Connection conn) {
        super(conn);
    }

    @Override
    public List<Hotel> get(HashMap<String, Object> filter) {
        return this.get();
    }

    @Override
    public List<Hotel> get() {
        try (Connection conn = this.getContext()) {
            String sql = "SELECT * FROM hotel LIMIT 1";
            PreparedStatement s = conn.prepareStatement(sql);

            ResultSet rs = s.executeQuery();

            if (rs.next()) {
                return List.of(
                        Hotel
                                .builder()
                                .hotelId(rs.getInt("hotel_id"))
                                .nome(rs.getString("nome"))
                                .endereco(rs.getString("endereco"))
                                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Hotel create(Hotel entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Hotel update(Hotel updatedEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Hotel entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
