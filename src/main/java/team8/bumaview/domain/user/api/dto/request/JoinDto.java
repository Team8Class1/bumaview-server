package team8.bumaview.domain.user.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JoinDto {

    private String email;
    private String id;
    private String password;
    private List<String> interest;
}
