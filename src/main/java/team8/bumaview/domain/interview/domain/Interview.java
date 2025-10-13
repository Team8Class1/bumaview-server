package team8.bumaview.domain.interview.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team8.bumaview.domain.answer.domain.Answer;
import team8.bumaview.domain.company.domain.Company;
import team8.bumaview.domain.interview.api.dto.request.ModifyInterviewDto;
import team8.bumaview.domain.interviewcategory.domain.InterviewCategory;
import team8.bumaview.domain.interviewgroup.domain.InterviewGroup;
import team8.bumaview.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024)
    private String question;
    private Date questionAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewCategory> interviewCategories = new ArrayList<>();

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InterviewGroup> interviewGroups = new ArrayList<>();

    public void update(ModifyInterviewDto modifyInterviewDto, Company company) {
        this.question = modifyInterviewDto.getQuestion();
        this.questionAt = modifyInterviewDto.getQuestionAt();
        this.company =  company;
    }
}
