package prueba.authservice.dto;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String tokenType = "Bearer";
    private String role;
    private String email;
    private Instant expiresAt;

    // Constructor personalizado
    public LoginResponseDTO(String token, String role, String email, Instant expiresAt) {
        this.token = token;
        this.role = role;
        this.email = email;
        this.expiresAt = expiresAt;
    }
}