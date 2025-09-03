package team8.bumaview.domain.interviewgroup.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.user.domain.User;

@Entity
@Table(name = "interview_group")
@Getter
public class InterviewGroup {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;
}
