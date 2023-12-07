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
import java.util.Calendar;
import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    private final String sqlInsert = "INSERT INTO reservations (check_in, check_out, adult_num, child_num, user_id, room, price) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String sqlQuery = "SELECT id, check_in, check_out, adult_num, child_num, user_id, room, price " +
                                    "FROM reservations";

    private final String sqlQueryByUser = "SELECT id, check_in, check_out, adult_num, child_num, user_id, room, price " +
                                            "FROM reservations WHERE user_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public Reservation include(Reservation reservation) throws Exception {
//        //TODO Reservation validation
//
//        try (Connection con = jdbcTemplate.getDataSource().getConnection();
//            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
//            ps.setDate(1, reservation.getCheckIn());
//            ps.setDate(2, reservation.getCheckOut());
//            ps.setInt(3, reservation.getAdultNum());
//            ps.setInt(4, reservation.getChildNum());
//            ps.setLong(5, reservation.getUser().getId());
//            ps.setLong(6, reservation.getRoom().getId());
//            ps.setFloat(7,calcDays(reservation.getCheckIn(), reservation.getCheckOut()) * reservation.getRoom().getDailyValue());
//
//            int result = ps.executeUpdate();
//
//            if (result == 1){
//                ResultSet tableKeys = ps.getGeneratedKeys();
//                tableKeys.next();
//                reservation.setId(tableKeys.getLong(1));
//                reservation.setPrice(calcDays(reservation.getCheckIn(), reservation.getCheckOut()) * reservation.getRoom().getDailyValue());
//
//                System.out.println("Reservation registered successfully!");
//                return reservation;
//            }
//            throw new Exception("Error! Reservation could not be registered!");
//        }
//    }

    public Reservation registerReservation(ReservationRequest reservationRequest) throws Exception{
        reservationValidation(reservationRequest);

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
            ps.setDate(1, reservationRequest.getCheckIn());
            ps.setDate(2, reservationRequest.getCheckOut());
            ps.setInt(3, reservationRequest.getAdultNum());
            ps.setInt(4, reservationRequest.getChildNum());
            ps.setLong(5, userRepository.getUserById(reservationRequest.userId).getId());
            ps.setLong(6, roomRepository.getRoomById(reservationRequest.room).getId());
            ps.setFloat(7,calcDays(reservationRequest.getCheckIn(), reservationRequest.getCheckOut()) * roomRepository.getRoomById(reservationRequest.room).getDailyValue());

            int result = ps.executeUpdate();

            if (result == 1){
                ResultSet tableKeys = ps.getGeneratedKeys();
                tableKeys.next();

                Reservation reservation = new Reservation(
                        tableKeys.getLong(1),
                        reservationRequest.checkIn,
                        reservationRequest.checkOut,
                        reservationRequest.adultNum,
                        reservationRequest.childNum,
                        userRepository.getUserById(reservationRequest.userId),
                        roomRepository.getRoomById(reservationRequest.room),
                        calcDays(reservationRequest.getCheckIn(), reservationRequest.getCheckOut()) * roomRepository.getRoomById(reservationRequest.room).getDailyValue()
                );

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

    public List<Reservation> listByUser(Long userId) throws Exception{
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlQueryByUser)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            List<Reservation> list = new ArrayList<>();

                while (rs.next()) {
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

    public Reservation updateReservation(Long id, ReservationRequest reservationRequest) throws Exception{
        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate)){
            ps.setDate(1, reservationRequest.getCheckIn());
            ps.setDate(2, reservationRequest.getCheckOut());
            ps.setInt(3, reservationRequest.getAdultNum());
            ps.setInt(4, reservationRequest.getChildNum());
            ps.setLong(5, userRepository.getUserById(reservationRequest.userId).getId());
            ps.setLong(6, roomRepository.getRoomById(reservationRequest.room).getId());
            ps.setFloat(7, calcDays(reservationRequest.getCheckIn(), reservationRequest.getCheckOut()) * roomRepository.getRoomById(reservationRequest.room).getDailyValue());
            ps.setLong(8, id);

            int result = ps.executeUpdate();

            if (result == 1){
                System.out.println("Reservation updated successfully!");

                Reservation reservation = getReservationById(id);
                reservation.setCheckIn(reservationRequest.checkIn);
                reservation.setCheckOut(reservationRequest.checkOut);
                reservation.setAdultNum(reservationRequest.adultNum);
                reservation.setChildNum(reservationRequest.childNum);
                reservation.setPrice(calcDays(reservation.getCheckIn(), reservation.getCheckOut()) * reservation.getRoom().getDailyValue());

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

    private void reservationValidation(ReservationRequest reservation) throws Exception{
        Calendar today = Calendar.getInstance();

        //checkIn validator
        if(reservation.getCheckIn() == null){
            throw new Exception("CheckIn date cannot be empty");
        }

        if(reservation.getCheckIn().compareTo(today.getTime())>= 0){
            throw new Exception("CheckIn date must be greater than or equal to the current day");
        }

        //checkOut validator
        if(reservation.getCheckOut() == null){
            throw new Exception("CheckOut date cannot be empty");
        }

        if(reservation.getCheckOut().compareTo(today.getTime()) > 0){
            throw new Exception("CheckOut date must be greater than to the current day");
        }

        if(reservation.getCheckOut().compareTo(reservation.getCheckIn()) > 0){
            throw new Exception("CheckOut date must be greater than to the checkIn date");
        }

        //adultNum validator
        if(reservation.getAdultNum() < 0){
            throw new Exception("AdultNum must be greater than 0");
        }

        //user validator
        String query = "SELECT * FROM users WHERE id = ?";

        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setLong(1, reservation.getUserId());
            try(ResultSet rs = ps.executeQuery()){
                if (!rs.next()){
                    throw new Exception("User not exists!");
                }
            }
        }

        //room validator
        query = "SELECT * FROM rooms WHERE id = ?";

        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setLong(1, reservation.getRoom());
            try(ResultSet rs = ps.executeQuery()){
                if (!rs.next()){
                    throw new Exception("Room not exists!");
                }
            }
        }

//        query = "SELECT room FROM reservations WHERE id = ?";
//
//        try(Connection con = jdbcTemplate.getDataSource().getConnection();
//            PreparedStatement ps = con.prepareStatement(query)){
//            ps.setLong(1, reservation.getId());
//            try(ResultSet rs = ps.executeQuery()){
//                if (!rs.next()){
//                    throw new Exception("Room is already reserved!");
//                }
//            }
//        }
    }
}
