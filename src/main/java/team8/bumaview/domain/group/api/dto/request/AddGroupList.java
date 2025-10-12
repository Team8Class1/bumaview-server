package team8.bumaview.domain.group.api.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddGroupList {

    private List<Long> interviewIdList = new ArrayList<>();

}
