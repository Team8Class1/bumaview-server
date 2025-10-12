package team8.bumaview.global.filter;

import io.jsonwebtoken.ExpiredJwtException;
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

        // 1. "Authorization" 헤더에서 "Bearer " 토큰 파싱
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // 토큰이 없는 경우, 다음 필터로 넘김 (공개된 엔드포인트 접근 허용)
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {
            // 2. 토큰 만료 여부 확인
            if (jwtUtil.isExpired(token)) {
                // 만료된 경우, 다음 필터로 넘기지 않고 401 에러 응답
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            }

            // 3. 토큰에서 username과 role 추출
            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);

            // 4. UserDetails 및 Authentication 객체 생성
            UserDto userDto = UserDto.builder()
                    .username(username)
                    .role(role)
                    .build();
            CustomUserDetails customUserDetails = new CustomUserDetails(userDto);

            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            // 5. SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {
            // isExpired() 체크와 별개로, 파싱 과정에서 만료 예외 발생 가능
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
            return;
        } catch (Exception e) {
            // 그 외 JWT 관련 예외 처리
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}