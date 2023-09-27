package poo.aps.hotelApp.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import poo.aps.hotelApp.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String sqlQuery = "SELECT id, name, desc, size, guests, dailyValue FROM rooms";

    public List<Room> listRooms() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlQuery);
             ResultSet rs = ps.executeQuery()) {
            List<Room> list = new ArrayList<>();

            while(rs.next()){
                Room room = new Room(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("desc"),
                        rs.getInt("size"),
                        rs.getInt("guests"),
                        rs.getFloat("dailyValue")
                );
                list.add(room);
            }

            return list;
        }
    }

    public Room getRoomById(Long id) throws Exception {
        List<Room> rooms = listRooms();

        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }
}
