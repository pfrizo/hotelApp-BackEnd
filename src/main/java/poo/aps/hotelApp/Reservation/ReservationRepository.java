package poo.aps.hotelApp.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import poo.aps.hotelApp.Room.RoomRepository;
import poo.aps.hotelApp.User.User;
import poo.aps.hotelApp.User.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    UserRepository userRepository;
    RoomRepository roomRepository;

    private final String sqlInsert = "INSERT INTO reservations (check_in, check_out, adult_num, child_num, user_id, room, price) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String sqlQuery = "SELECT id, check_in, check_out, adult_num, child_num, user_id, room, price " +
                                    "FROM reservations";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Reservation include(Reservation reservation) throws Exception {
        //TODO Reservation validation

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, reservation.getCheckIn());
            ps.setDate(2, reservation.getCheckOut());
            ps.setInt(3, reservation.getAdultNum());
            ps.setInt(4, reservation.getChildNum());
            ps.setLong(5, reservation.getUser().getId());
            ps.setLong(6, reservation.getRoom().getId());
            ps.setFloat(7,calcDays(reservation.getCheckIn(), reservation.getCheckOut()) * reservation.getRoom().getDailyValue());

            int result = ps.executeUpdate();

            if (result == 1){
                ResultSet tableKeys = ps.getGeneratedKeys();
                tableKeys.next();
                reservation.setId(tableKeys.getLong(1));
                reservation.setPrice(calcDays(reservation.getCheckIn(), reservation.getCheckOut()) * reservation.getRoom().getDailyValue());

                System.out.println("Reservation registered successfully!");
                return reservation;
            }
            throw new Exception("Error! Reservation could not be registered!");
        }
    }

    private long calcDays(Date checkIn, Date checkOut) {
        LocalDate in = checkIn.toLocalDate();
        LocalDate out = checkOut.toLocalDate();

        return ChronoUnit.DAYS.between(in, out);
    }

    public List<Reservation> list() throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlQuery);
             ResultSet rs = ps.executeQuery()) {
            List<Reservation> list = new ArrayList<>();

            while(rs.next()){
                Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getDate("check_in"),
                    rs.getDate("check_out"),
                    rs.getInt("adult_num"),
                    rs.getInt("child_num"),
                    userRepository.getUserById(rs.getLong("user_id")),
                    roomRepository.getRoomById(rs.getLong("room")),
                    rs.getFloat("price")
                );
                list.add(reservation);
            }
            return list;
        }
    }

    public Reservation getReservationById(Long id) throws Exception {
        List<Reservation> reservations = list();

        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }

    private String sqlUpdate = "UPDATE reservations SET " +
            "check_in = ?, " +
            "check_out = ?, " +
            "adult_num = ?, " +
            "child_num = ?, " +
            "user_id = ?, " +
            "room = ?, " +
            "price = ? " +
            "WHERE id = ?";

    public Reservation updateReservation(Long id, Reservation reservation) throws Exception{
        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate)){
            ps.setDate(1, (Date) reservation.getCheckIn());
            ps.setDate(2, (Date) reservation.getCheckOut());
            ps.setInt(3, reservation.getAdultNum());
            ps.setInt(4, reservation.getChildNum());
            ps.setLong(5, reservation.getUser().getId());
            ps.setLong(6, reservation.getRoom().getId());
            ps.setFloat(7, reservation.getPrice());
            ps.setLong(8, reservation.getId());

            int result = ps.executeUpdate();

            if (result == 1){
                System.out.println("Reservation updated successfully!");
                reservation.setId(id);
                return reservation;
            }
            throw new Exception("ERROR! Reservation could not be updated!");
        }
    }

    private String sqlDelete = "DELETE FROM reservations WHERE id = ?";

    public Reservation deleteReservation(Long id) throws Exception{
        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlDelete)){

            ps.setLong(1, id);

            int result = ps.executeUpdate();

            if (result == 1){
                System.out.println("Reservation deleted successfully!");
                return null;
            }

            throw new Exception("ERROR! Reservation could not be deleted!");
        }
    }
}
