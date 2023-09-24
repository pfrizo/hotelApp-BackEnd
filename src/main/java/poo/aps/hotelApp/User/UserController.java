package poo.aps.hotelApp.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poo.aps.hotelApp.Response;

@RestController("/api/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @PostMapping("/")
    public Response include(@RequestBody User user) {
        try {
            return new Response(repository.include(user));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("")
    public Response list() {
        try {
            return new Response(repository.listUsers());
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Response getUserById(@PathVariable int id) {
        try {
            return new Response(repository.getUserById(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}
