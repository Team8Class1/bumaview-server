package team8.bumaview.domain.interviewcategory.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team8.bumaview.domain.category.domain.Category;
import team8.bumaview.domain.interview.domain.Interview;

@Entity
@Table(name = "interview_category")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
