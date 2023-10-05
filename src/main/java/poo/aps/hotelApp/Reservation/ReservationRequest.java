package poo.aps.hotelApp.Reservation;

import lombok.Data;

import java.sql.Date;

@Data
public class ReservationRequest {
    String cpf;
    String email;
    Date checkIn;
    Date checkOut;
    int adultNum;
    int childNum;
    Long room;
}
