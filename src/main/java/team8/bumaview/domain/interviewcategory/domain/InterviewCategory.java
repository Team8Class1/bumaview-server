package team8.bumaview.domain.interviewcategory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team8.bumaview.domain.category.domain.Category;
import team8.bumaview.domain.interview.domain.Interview;

@Entity
@Table(name = "interview_category")
@Getter
public class InterviewCategory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
