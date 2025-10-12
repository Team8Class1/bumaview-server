package team8.bumaview.domain.interview.api.dto.response;

import lombok.*;
import team8.bumaview.domain.category.api.dto.response.CategoryList;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllInterviewDto {
    private Long interviewId;
    private String question;
    private List<CategoryList> categoryList;
    private Long companyId;
    private String companyName;
    private Date questionAt;
}
