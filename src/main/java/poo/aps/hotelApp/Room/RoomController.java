package poo.aps.hotelApp.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import poo.aps.hotelApp.Response;
import poo.aps.hotelApp.User.UserRepository;

@RestController("api/room")
public class RoomController {

    @Autowired
    RoomRepository repository;

    @GetMapping("")
    public Response list() {
        try {
            return new Response(repository.listRooms());
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Response getRoomById(@PathVariable Long id) {
        try {
            return new Response(repository.getRoomById(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}
