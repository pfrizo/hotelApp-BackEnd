package poo.aps.hotelApp.Reservation;

import lombok.Data;

import java.sql.Date;

@Data
public class ReservationRequest {
    Date checkIn;
    Date checkOut;
    int adultNum;
    int childNum;
    Long userId;
    Long room;
}
