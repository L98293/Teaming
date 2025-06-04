package Solo.study.Config;

import Solo.study.Jwt.JwtProvider;
import Solo.study.Service.MemberDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 토큰을 Authorization 헤더가 아닌 쿠키에서 추출하도록 변경
 * 이유: 보안 및 클라이언트 편의성 증대를 위해 토큰을 HttpOnly 쿠키에 저장하는 경우가 많음
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;


    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String token = resolveToken(request);
        if (token != null && jwtProvider.validateToken(token)) {
            String username = jwtProvider.getUsername(token);
            UserDetails userDetails = memberDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }


    /**
     * 쿠키에서 "token" 이름의 JWT 토큰을 추출
     * @param request HttpServletRequest
     * @return 토큰 문자열 또는 null
     */
    protected String resolveToken(HttpServletRequest request) {
        // Authorization 헤더 대신 쿠키에서 토큰을 읽도록 변경
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

// 참고: 로그인 컨트롤러에서 JWT 토큰을 생성한 후 HttpOnly, Secure 플래그가 설정된 쿠키에 토큰을 저장하여 응답해야 합니다.
// 이 부분은 로그인 컨트롤러 코드에 추가해야 합니다.
