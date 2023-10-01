package poo.aps.hotelApp.User;

import com.sun.source.tree.ReturnTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import poo.aps.hotelApp.Response;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @PostMapping("/register")
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
    public Response getUserById(@PathVariable Long id) {
        try {
            return new Response(repository.getUserById(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody User user){
        try{
            return new Response(repository.updateUser(id, user));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}
