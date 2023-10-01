package poo.aps.hotelApp.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import poo.aps.hotelApp.Room.RoomRepository;
import poo.aps.hotelApp.User.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    UserRepository userRepository;
    RoomRepository roomRepository;

    private final String sqlInsert = "INSERT INTO reservations (checkIn, checkOut, adultNum, childNum, userId, room, price) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)";

    private final String sqlQuery = "SELECT id, checkIn, checkOut, adultNum, childNum, userId, room, price " +
                                    "FROM reservations";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Reservation include(Reservation reservation) throws Exception {
        //TODO Reservation validation

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, (Date) reservation.getCheckIn());
            ps.setDate(2, (Date) reservation.getCheckOut());
            ps.setInt(3, reservation.getAdultNum());
            ps.setInt(4, reservation.getChildNum());
            ps.setLong(5, reservation.getUser().getId());
            ps.setLong(6, reservation.getRoom().getId());
            ps.setFloat(7, reservation.getValue());

            int result = ps.executeUpdate();

            if (result == 1){
                ResultSet tableKeys = ps.getGeneratedKeys();
                tableKeys.next();
                reservation.setId(tableKeys.getLong(1));

                System.out.println("Reservation registered successfully!");
                return reservation;
            }
            throw new Exception("Error! Reservation could not be registered!");
        }
    }

    public List<Reservation> list() throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlQuery);
             ResultSet rs = ps.executeQuery()) {
            List<Reservation> list = new ArrayList<>();

            while(rs.next()){
                Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getDate("checkIn"),
                    rs.getDate("checkOut"),
                    rs.getInt("adultNum"),
                    rs.getInt("childNum"),
                    userRepository.getUserById(rs.getLong("userId")),
                    roomRepository.getRoomById(rs.getLong("room")),
                    rs.getFloat("price")
                );
                list.add(reservation);
            }
            return list;
        }
    }
}
