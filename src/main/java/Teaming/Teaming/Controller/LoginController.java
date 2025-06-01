package Teaming.Teaming.Controller;

import Teaming.Teaming.DTO.LoginDTO;
import Teaming.Teaming.Entity.User;
import Teaming.Teaming.Jwt.JwtProvider;
import Teaming.Teaming.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        User user = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 사용자입니다."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.createToken(user.getUsername());

        return ResponseEntity.ok("로그인 성공");

    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody LoginDTO request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGithubId(request.getGithubId());
        user.setRole("ROLE_USER");
        memberRepository.save(user);

        return ResponseEntity.ok("회원가입 끝~!");
    }
}
