package team8.bumaview.domain.answer.api.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerDto {
    private Long interviewId;
    private String answer;
    private Boolean isPrivate;
}
