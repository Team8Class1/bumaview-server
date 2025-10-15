package team8.bumaview.domain.user.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team8.bumaview.domain.favorite.domain.Favorite;
import team8.bumaview.domain.favorite.persistence.FavoriteRepository;
import team8.bumaview.domain.user.api.dto.UserDto;
import team8.bumaview.domain.user.api.dto.request.JoinDto;
import team8.bumaview.domain.user.api.dto.response.UserInfoDto;
import team8.bumaview.domain.user.domain.User;
import team8.bumaview.domain.user.persistence.UserRepository;
import team8.bumaview.domain.userfavorite.domain.UserFavorite;
import team8.bumaview.domain.userfavorite.persistence.UserFavoriteRepository;
import team8.bumaview.global.exception.AlreadyExistException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import team8.bumaview.domain.user.api.dto.CustomUserDetails;
import team8.bumaview.global.util.JwtUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FavoriteRepository favoriteRepository;
    private final UserFavoriteRepository userFavoriteRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void joinProcess(JoinDto joinDTO) throws AlreadyExistException {

        String id = joinDTO.getId();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUserId(id);

        if (isExist) {
            throw new AlreadyExistException("이미 존재하는 유저 이름입니다.");
        }

        List<Favorite> favorites = favoriteRepository.findByNameIn(joinDTO.getInterest());
        User user = User.create(id, bCryptPasswordEncoder.encode(password), joinDTO.getEmail());

        userRepository.save(user);

        for(Favorite favorite : favorites) {
            UserFavorite userFavorite = UserFavorite.builder()
                    .user(user)
                    .favorite(favorite)
                    .build();
            userFavoriteRepository.save(userFavorite);
        }
    }

    @Transactional(readOnly = true)
    public String login(String username, String password) {
        // 1. 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password, null));

        // 2. 인증 성공 시 UserDetails 가져오기
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        // 3. 역할(Role) 정보 가져오기
        Long id = customUserDetails.getUserDto().getId();
        String email = customUserDetails.getUserDto().getEmail();
        String role = customUserDetails.getAuthorities().iterator().next().getAuthority();

        // 4. JWT 생성하여 반환
        return jwtUtil.createToken(username, role, id, email,1000L * 60 * 60 * 2);
    }

    public UserInfoDto getInfo(Long userId) {
        User user = userRepository.findWithFavoriteById(userId).orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
        return user.toUserInfoDto(user.getUserFavorites());
    }
}