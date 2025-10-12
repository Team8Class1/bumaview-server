package team8.bumaview.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team8.bumaview.domain.user.api.dto.request.JoinDto;
import team8.bumaview.domain.user.application.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService joinService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDto joinDto) {

        System.out.println(joinDto.getUsername());
        joinService.joinProcess(joinDto);

        return "ok";
    }


}
