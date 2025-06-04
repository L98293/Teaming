package Solo.study.Jwt;

import Solo.study.DTO.MemberDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key Key;
    private final long validityInMilliseconds = 3600000;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.Key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 토큰을 생성하는 메서드입니다. (메서드명 오타 수정: crateToken -> createToken)
    // 토큰에 username과 role 정보를 포함시켜 이후 인증 필터에서 권한을 식별할 수 있도록 합니다.
    public String createToken(String username, String role) {
        Date now = new Date();
        Date setTime =  new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                // 역할(role) 정보를 JWT에 포함시켜야 이후 인증 필터에서 권한을 식별할 수 있다.
                .claim("role", role) // claim key 수정: "Role" -> "role"
                .setIssuedAt(now)
                .setExpiration(setTime)
                .signWith(Key)
                .compact();
    }


    // 토큰에서 username을 추출하는 메서드입니다.
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 토큰에서 권한(role) 정보를 추출하여 인증 필터에서 사용하기 위해 추가합니다.
    public String getRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

// 로그인 컨트롤러에서는 JWT 토큰을 응답 쿠키에 HttpOnly, Secure 속성으로 설정해야 합니다.
// 예: response.addCookie()를 사용해 "token" 이름의 쿠키를 생성하고, 클라이언트에서 안전하게 토큰을 전달받도록 합니다.
