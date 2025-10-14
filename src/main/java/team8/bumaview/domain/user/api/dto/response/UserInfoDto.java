package team8.bumaview.domain.user.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team8.bumaview.domain.user.domain.Role;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class UserInfoDto {
    private Long userSequenceId;
    private String userId;
    private String email;
    private Role role;
    private Date birthday;
    private List<String> favoriteList;
}
