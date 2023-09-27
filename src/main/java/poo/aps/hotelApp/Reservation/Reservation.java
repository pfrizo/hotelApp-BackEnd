package poo.aps.hotelApp.Reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poo.aps.hotelApp.User.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private Date checkIn;
    private Date checkOut;
    private int adultNum;
    private int childNum;
    private User user;
    private float value;
}
