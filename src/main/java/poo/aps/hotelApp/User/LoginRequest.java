package poo.aps.hotelApp.User;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
