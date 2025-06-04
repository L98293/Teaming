package Solo.study.Controller;

import java.util.Map;

import Solo.study.DTO.MemberDTO;
import Solo.study.Entity.MemberEntity;
import Solo.study.Jwt.JwtProvider;
import Solo.study.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO) {
        // user가 존재하는가
        MemberEntity user = memberRepository.findByUsername(memberDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 사용자입니다."));
        //비밀번호가 맞는가?
        if (!passwordEncoder.matches(memberDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        //그럼 로그인

        String token = jwtProvider.createToken(user.getUsername(), user.getRole());

        // JWT 토큰을 HttpOnly 쿠키에 설정하여 응답 (클라이언트에서 접근 불가능하도록 보안 강화)
        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true) // JavaScript에서 접근 불가능
            .secure(true) // HTTPS 환경에서만 전송됨 (운영 환경 기준)
            .path("/") // 모든 경로에 대해 쿠키 전송
            .maxAge(3600) // 쿠키 유효 시간: 1시간
            .sameSite("Strict") // CSRF 방지 설정
            .build();

        return ResponseEntity.ok()
            .header("Set-Cookie", cookie.toString())
            .body(Map.of("message", "로그인 성공")); // 바디는 단순 메시지만 전달
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }

        MemberEntity user = new MemberEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        memberRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "회원가입 성공")); // 응답을 JSON 형식으로 변경하여 클라이언트가 일관되게 처리 가능하도록 함
    }
}
