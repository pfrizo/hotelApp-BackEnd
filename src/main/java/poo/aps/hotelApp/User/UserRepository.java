package poo.aps.hotelApp.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import poo.aps.hotelApp.Reservation.Reservation;
import poo.aps.hotelApp.Validators.CpfValidator;
import poo.aps.hotelApp.Validators.EmailValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final String sqlInsert = "INSERT INTO users (first_name, last_name, email, password, cpf) " +
            "VALUES (?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User include(User user) throws Exception {
        //TODO User validation
        userValidation(user, true);

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getCpf());

            int result = ps.executeUpdate();

            if (result == 1){
                ResultSet tableKeys = ps.getGeneratedKeys();
                tableKeys.next();
                user.setId(tableKeys.getLong(1));

                System.out.println("User entered successfully!");
                return user;
            }
            throw new Exception("ERROR! User could not be entered!");
        }
    }

    private final String sqlQuery = "SELECT id, first_name, last_name, email, password, cpf FROM users";

    public List<User> listUsers() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery()) {
            List<User> list = new ArrayList<>();

            while(rs.next()){
                User user = new User(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("cpf")
                );
                list.add(user);
            }

            return list;
        }
    }

    public User getUserById(Long id) throws Exception {
        List<User> users = listUsers();

        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    private String sqlUpdate = "UPDATE users SET " +
            "first_name = ?, " +
            "last_name = ?, " +
            "email = ?, " +
            "password = ?, " +
            "cpf = ? " +
            "WHERE id = ?";

    public User updateUser(Long id, User user) throws Exception{
        user.setId(id);
        userValidation(user, false);

        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlUpdate)){
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getCpf());
            ps.setLong(6, id);

            int result = ps.executeUpdate();

            if (result == 1){
                System.out.println("User updated successfully!");
                return user;
            }
            throw new Exception("ERROR! User could not be updated!");
        }
    }

    private String sqlDelete = "DELETE FROM users WHERE id = ?";

    public User deleteUser(Long id) throws Exception{
        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlDelete)){

            ps.setLong(1, id);

            int result = ps.executeUpdate();

            if (result == 1){
                System.out.println("User deleted successfully!");
                return null;
            }

            throw new Exception("ERROR! User could not be deleted!");
        }
    }

    private void userValidation(User user, boolean inserting) throws Exception {
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()){
            throw new Exception("First name cannot be empty");
        }
        user.setFirstName(user.getFirstName().trim());

        if (user.getLastName() == null || user.getLastName().trim().isEmpty()){
            throw new Exception("Last name cannot be empty");
        }
        user.setLastName(user.getLastName().trim());

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()){
            throw new Exception("E-mail cannot be empty");
        }
        EmailValidator.validate(user.getEmail());
        user.setEmail(user.getEmail().trim());

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()){
            throw new Exception("Password cannot be empty");
        }

        if (user.getCpf() == null || user.getCpf().trim().isEmpty()){
            throw new Exception("CPF cannot be empty");
        }
        if(!CpfValidator.validate(user.getCpf())){
            throw new IllegalArgumentException("Invalid CPF!");
        }
        user.setCpf(CpfValidator.formatCpf(user.getCpf().trim()));

        String query = "SELECT id FROM users WHERE email = ?";

        try(Connection con = jdbcTemplate.getDataSource().getConnection();
        PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, user.getEmail());
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    if (inserting || rs.getInt("id") != user.getId()){
                        throw new Exception("E-mail already registered!");
                    }
                }
            }
        }

        query = "SELECT id FROM users WHERE cpf = ?";

        try(Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, user.getCpf());
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    if (inserting || rs.getInt("id") != user.getId()){
                        throw new Exception("CPF already registered!");
                    }
                }
            }
        }
    }
}
