package team8.bumaview.domain.interview.api.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UploadInterviewDto {

    private String question;
    private List<Long> categoryList;
    private Long companyId;
    private Date questionAt;
}
