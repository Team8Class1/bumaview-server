package team8.bumaview.domain.interview.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import team8.bumaview.domain.category.api.dto.response.CategoryList;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyInterviewDto {
    private String question;
    private Long companyId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date questionAt;
    private List<Long> category;
}
