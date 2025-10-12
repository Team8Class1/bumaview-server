package team8.bumaview.domain.answer.api.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Long userSequenceId;
    private String userId;
    private Long answerId;
    private String answer;
    private int like;
}
