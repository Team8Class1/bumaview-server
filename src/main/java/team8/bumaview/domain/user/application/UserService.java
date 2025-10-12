package team8.bumaview.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team8.bumaview.domain.user.api.dto.request.JoinDto;
import team8.bumaview.domain.user.domain.User;
import team8.bumaview.domain.user.persistence.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUserId(username);

        if (isExist) {

            return;
        }

        User data = User.create(username, bCryptPasswordEncoder.encode(password));

        userRepository.save(data);
    }
}
