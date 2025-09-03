package team8.bumaview.domain.favorite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team8.bumaview.domain.user.domain.User;

@Entity
@Getter
public class Favorite {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
