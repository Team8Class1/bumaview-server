package team8.bumaview.domain.group.api.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDto {

    private Long groupId;
    private String name;

}
