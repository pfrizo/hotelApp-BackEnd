package poo.aps.hotelApp.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    @Autowired
    ReservationRepository repository;


}