package team8.bumaview.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String password;
    private Role role;
    private Date birthday;

    public static User create(String userId, String password) {
        User user = new User();
        user.userId = userId;
        user.password = password;
        return user;
    }
}
