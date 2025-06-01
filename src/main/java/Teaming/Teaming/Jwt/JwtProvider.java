package Teaming.Teaming.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.KeyGenerators;
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

    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Key)
                .compact();
    }

    //Jwt 토큰에서 사용자의 이름을 추출함

    public String getSubject(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(Key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }



    //토큰이 유효한지 확인

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
