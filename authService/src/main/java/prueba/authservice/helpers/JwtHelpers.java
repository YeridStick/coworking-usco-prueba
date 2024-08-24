package prueba.authservice.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
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

    @Value("${jwt.expiration:3600}") // Default to 1 hour if not specified
    private Long jwtExpirationInSeconds;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createTokenWithRole(String correo, String role) {
        final Instant now = Instant.now();
        final Instant expirationDate = now.plusSeconds(jwtExpirationInSeconds);

        return Jwts.builder()
                .setSubject(correo)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationDate))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Error validating token", e);
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Failed to parse JWT token", e);
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}