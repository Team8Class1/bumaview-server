package team8.bumaview.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import team8.bumaview.domain.user.api.dto.CustomUserDetails;
import team8.bumaview.domain.user.api.dto.UserDto;
import team8.bumaview.global.util.JwtUtil;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader == null && !authorizationHeader.startsWith("Bearer ")) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        String token = authorizationHeader.substring(7);
//
//        if(jwtUtil.isExpired(token)){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        UserDto userDto = UserDto.builder()
//                .username(jwtUtil.getUsername(token))
                .username("임시이름")
                .password("임시비번")
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(userDto);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
