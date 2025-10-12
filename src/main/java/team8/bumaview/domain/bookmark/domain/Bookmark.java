package team8.bumaview.domain.bookmark.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isBookmarked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    public void updateBookmark() {
        System.out.println("isBookmarked = " + isBookmarked);
        isBookmarked = !isBookmarked;
        System.out.println("#############Change##########");
        System.out.println("isBookmarked = " + isBookmarked);
    }
}
