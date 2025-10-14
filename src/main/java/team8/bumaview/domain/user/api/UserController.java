package team8.bumaview.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team8.bumaview.domain.user.api.dto.CustomUserDetails;
import team8.bumaview.domain.user.api.dto.UserDto;
import team8.bumaview.domain.user.api.dto.request.JoinDto;
import team8.bumaview.domain.user.api.dto.request.LoginRequestDto;
import team8.bumaview.domain.user.api.dto.response.UserInfoDto;
import team8.bumaview.domain.user.application.UserService;
import team8.bumaview.global.exception.AlreadyExistException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> joinProcess(@RequestBody JoinDto joinDto) {

        System.out.println(joinDto.getId());

        try {
            userService.joinProcess(joinDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(AlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디 중복");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            String jwt = userService.login(loginRequestDto.getId(), loginRequestDto.getPassword());
            // JWT를 JSON 형태로 반환
            return ResponseEntity.ok(Map.of("accessToken", "Bearer " + jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    @GetMapping
    public ResponseEntity<UserInfoDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserDto().getId();
        return ResponseEntity.status(HttpStatus.OK).body(userService.getInfo(userId));
    }
}
