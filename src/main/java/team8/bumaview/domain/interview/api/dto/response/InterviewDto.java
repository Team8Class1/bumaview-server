package team8.bumaview.domain.interview.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team8.bumaview.domain.answer.api.dto.response.AnswerDto;
import team8.bumaview.domain.category.api.dto.response.CategoryList;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class InterviewDto {
    private Long interviewId;
    private String question;
    private Long companyId;
    private String companyName;
    private Date questionAt;
    private List<CategoryList>  categoryList;
    private List<AnswerDto> answer;
}
