package poo.aps.hotelApp.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poo.aps.hotelApp.Response;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationRepository repository;

//    @PostMapping("/")
//    @CrossOrigin(origins = "http://127.0.0.1:5500/")
//    public Response createReservation(@RequestBody Reservation reservation){
//        try{
//            return new Response(repository.include(reservation));
//        } catch (Exception e){
//            return new Response(e.getMessage());
//        }
//    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response registerReservation(@RequestBody ReservationRequest reservationRequest){
        try{
            return new Response(repository.registerReservation(reservationRequest));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/listAll")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response listAll(){
        try{
            return new Response(repository.list());
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/listByUser/{userId}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response listByUser(@PathVariable Long userId){
        try {
            return new Response(repository.listByUser(userId));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response getReservationById(@PathVariable Long id){
        try{
            return new Response(repository.getReservationById(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response updateReservation(@PathVariable Long id, @RequestBody ReservationRequest reservationRequest){
        try{
            return new Response(repository.updateReservation(id, reservationRequest));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response deleteReservation(@PathVariable Long id){
        try{
           return new Response(repository.deleteReservation(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}