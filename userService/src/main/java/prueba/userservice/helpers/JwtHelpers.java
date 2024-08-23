package prueba.userservice.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JwtHelpers {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createToken(String userName) {
        final var now = Instant.now();
        final var expirationDate = now.plusSeconds(3600); // 1 hour
        return Jwts.builder()
                .subject(userName)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationDate))
                .signWith(getSecretKey())
                .compact();
    }

    public String createTokenWithRole(String userName, String role) {
        final var now = Instant.now();
        final var expirationDate = now.plusSeconds(3600); // 1 hour
        return Jwts.builder()
                .subject(userName)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationDate))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return !claims.getExpiration().before(Date.from(Instant.now()));
        } catch (Exception e) {
            log.error("Error validating token", e);
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT token: " + e.getMessage(), e);
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}