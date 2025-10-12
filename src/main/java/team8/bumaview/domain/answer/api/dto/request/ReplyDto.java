package team8.bumaview.domain.answer.api.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {
    private Long interviewId;
    private String answer;
    private Boolean isPrivate;
    private Long parentAnswerId;
}
