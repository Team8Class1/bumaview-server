package team8.bumaview.domain.user.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import team8.bumaview.domain.user.domain.Role;

@Getter
@Setter
@Builder
@ToString
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;

}
