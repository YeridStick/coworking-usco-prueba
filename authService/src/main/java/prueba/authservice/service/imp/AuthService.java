package prueba.authservice.service.imp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prueba.authservice.dto.LoginResponseDTO;
import prueba.authservice.dto.userDto.UserAuthDto;
import prueba.authservice.dto.userDto.UserGinDTO;
import prueba.authservice.helpers.JwtHelpers;
import prueba.authservice.client.UserClient;

import javax.naming.AuthenticationException;
import java.time.Instant;

@Service
public class AuthService {

    private final UserClient userClient;
    private final JwtHelpers jwtHelpers;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserClient userClient, JwtHelpers jwtHelpers, PasswordEncoder passwordEncoder) {
        this.userClient = userClient;
        this.jwtHelpers = jwtHelpers;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO authenticate(UserGinDTO userGinDTO) throws AuthenticationException {
        UserAuthDto userAuthDto = userClient.getUserByEmail(userGinDTO.getCorreo());

        if (userAuthDto == null || !passwordEncoder.matches(userGinDTO.getPassword(), userAuthDto.getPassword())) {
            throw new AuthenticationException("Credenciales inválidas");
        }

        String token = jwtHelpers.createTokenWithRole(userAuthDto.getCorreo(), userAuthDto.getRoll());
        Instant expiresAt = Instant.now().plusSeconds(3600); // Asumiendo que el token expira en 1 hora

        return new LoginResponseDTO(token, userAuthDto.getRoll(), userAuthDto.getCorreo(), expiresAt);
    }
}
