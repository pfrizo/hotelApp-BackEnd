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
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response register(@RequestBody User user) {
        try {
            return new Response(repository.include(user));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response login(@RequestBody LoginRequest loginRequest){
        try{
            return new Response(repository.login(loginRequest));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/listAll")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response list() {
        try {
            return new Response(repository.listUsers());
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response getUserById(@PathVariable Long id) {
        try {
            return new Response(repository.getUserById(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response update(@PathVariable Long id, @RequestBody User user){
        try{
            return new Response(repository.updateUser(id, user));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    public Response delete(@PathVariable Long id){
        try{
            return new Response(repository.deleteUser(id));
        } catch (Exception e){
            return new Response(e.getMessage());
        }
    }
}
