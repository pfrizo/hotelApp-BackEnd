package poo.aps.hotelApp.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poo.aps.hotelApp.Response;
import poo.aps.hotelApp.User.UserRepository;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    RoomRepository repository;

    @GetMapping("/listAll")
    public Response list() {
        try {
            return new Response(repository.listRooms());
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response getRoomById(@PathVariable Long id) {
        try {
            return new Response(repository.getRoomById(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}
