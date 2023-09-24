package poo.aps.hotelApp.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import poo.aps.hotelApp.User.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    private final UserRepository userRepository;
    public ReservationRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final String sqlInsert = "INSERT INTO reservations (check_in, check_out, adult_num, child_num, user_id, value) " +
                                    "VALUES (?, ?, ?, ?, ?, ?)";

    private final String sqlQuery = "SELECT id, check_in, check_out, adult_num, child_num, user_id, value " +
                                    "FROM reservations";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Reservation include(Reservation reservation) throws Exception {
        //TODO Reservation validation

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, (Date) reservation.getCheckInDate());
            ps.setDate(2, (Date) reservation.getCheckOutDate());
            ps.setInt(3, reservation.getAdultNum());
            ps.setInt(4, reservation.getChildNum());
            ps.setInt(5, reservation.getUser().getId());
            ps.setFloat(6, reservation.getValue());

            int result = ps.executeUpdate();

            if (result == 1){
                ResultSet tableKeys = ps.getGeneratedKeys();
                tableKeys.next();
                reservation.setId(tableKeys.getInt(1));

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
                    rs.getInt("id"),
                    rs.getDate("check_in"),
                    rs.getDate("check_out"),
                    rs.getInt("adult_num"),
                    rs.getInt("child_num"),
                    userRepository.getUserById(rs.getInt("user_id")),
                    rs.getFloat("value")
                );
                list.add(reservation);
            }
            return list;
        }
    }
}
