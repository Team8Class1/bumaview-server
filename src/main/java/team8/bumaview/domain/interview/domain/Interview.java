package team8.bumaview.domain.interview.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team8.bumaview.domain.user.domain.User;

import java.util.Date;

@Entity
@Getter
public class Interview {

    @Id
    @GeneratedValue
    private Long id;

    private String question;
    private Date questionAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
