package team8.bumaview.domain.answer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.user.domain.User;

@Entity
@Getter
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    private String answer;
    private String isPrivate;
    private int like;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;
}
