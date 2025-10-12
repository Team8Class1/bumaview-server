package team8.bumaview.domain.user.api.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String id;
    private String password;
}
