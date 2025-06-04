package Solo.study.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // HTTP 기본 인증 방식 사용 안 함 (JWT 사용)
            .httpBasic().disable()
            // CSRF 보호 비활성화 (JWT는 CSRF 필요 없음)
            .csrf().disable()
            // CORS 설정 활성화
            .cors().and()
            // 세션을 완전히 사용하지 않도록 설정 (JWT는 무상태 Stateless 방식)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // form 기반 로그인 기능 비활성화 (우리는 JWT 로그인 방식 사용)
            .formLogin().disable()
            // 경로별 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 로그인/회원가입은 인증 없이 접근 가능
                .requestMatchers("/", "/fonts/**", "/member/login", "/member/signup").permitAll()
                // 관리자 페이지는 ADMIN 권한 필요
                .requestMatchers("/admin").hasRole("ADMIN")
                // 메인 페이지는 USER 또는 ADMIN 권한 필요
                .requestMatchers("/main").hasAnyRole("USER", "ADMIN")
                // 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 클라이언트 주소를 명시적으로 설정하여 쿠키 기반 인증 시 CORS 문제 방지
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        // 쿠키를 포함한 인증 정보를 허용하도록 설정 (withCredentials: true)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}