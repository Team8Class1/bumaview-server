package team8.bumaview.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team8.bumaview.domain.user.api.dto.CustomUserDetails;
import team8.bumaview.domain.user.api.dto.UserDto;
import team8.bumaview.domain.user.domain.User;
import team8.bumaview.domain.user.persistence.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        UserDto userDto = UserDto.builder()
                .username(username)
                .password(user.getPassword())
                .build();
        return new CustomUserDetails(userDto);
    }
}
