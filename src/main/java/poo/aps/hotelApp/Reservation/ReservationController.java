package poo.aps.hotelApp.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poo.aps.hotelApp.Response;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationRepository repository;

    @PostMapping("/")
    public Response createReservation(@RequestBody Reservation reservation){
        try{
            return new Response(repository.include(reservation));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/listAll")
    public Response listAll(){
        try{
            return new Response(repository.list());
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Response getReservationById(@PathVariable Long id){
        try{
            return new Response(repository.getReservationById(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Response updateReservation(@PathVariable Long id, @RequestBody Reservation reservation){
        try{
            return new Response(repository.updateReservation(id, reservation));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Response deleteReservation(@PathVariable Long id){
        try{
           return new Response(repository.deleteReservation(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}