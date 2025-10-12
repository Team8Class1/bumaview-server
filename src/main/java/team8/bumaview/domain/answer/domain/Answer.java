package team8.bumaview.domain.answer.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team8.bumaview.domain.answer.api.dto.request.AnswerDto;
import team8.bumaview.domain.interview.domain.Interview;
import team8.bumaview.domain.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;
    private Boolean isPrivate;
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Answer parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> children = new ArrayList<>();

    public void addReply(Answer child) {
        children.add(child);
        child.parent = this;
    }

    public void removeReply(Answer child) {
        children.remove(child);
        child.parent = null;
    }

    public void modify(AnswerDto answerDto) {
        this.answer = answerDto.getAnswer();
        this.isPrivate = answerDto.getIsPrivate();
    }
}
