package poo.aps.hotelApp.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/reservations")
public class ReservationController {

    @Autowired
    ReservationRepository repository;


}