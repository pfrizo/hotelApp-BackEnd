package poo.aps.hotelApp.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final String sqlInsert = "INSERT INTO users (firstName, lastName, email, password, cpf) " +
            "VALUES (?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User include(User user) throws Exception {
        //TODO User validation

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

    private final String sqlQuery = "SELECT id, firstName, lastName, email, password, cpf FROM users";

    public List<User> listUsers() throws Exception {
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery()) {
            List<User> list = new ArrayList<>();

            while(rs.next()){
                User user = new User(
                    rs.getLong("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
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
}
