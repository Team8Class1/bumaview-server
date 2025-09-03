package team8.bumaview.domain.bookmark.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.user.domain.User;

@Entity
@Getter
public class Bookmark {

    @Id
    @GeneratedValue
    private Long id;

    private boolean isBookmarked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;
}
