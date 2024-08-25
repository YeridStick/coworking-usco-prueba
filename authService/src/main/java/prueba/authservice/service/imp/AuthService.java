package prueba.authservice.service.imp;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prueba.authservice.dto.LoginResponseDTO;
import prueba.authservice.dto.autenticacion.TwoFactorResponseDTO;
import prueba.authservice.dto.email.EmailDTO;
import prueba.authservice.dto.userDto.UserAuthDto;
import prueba.authservice.dto.userDto.UserGinDTO;
import prueba.authservice.helpers.JwtHelpers;
import prueba.authservice.client.UserClient;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Random;

@Service
public class AuthService {

    private final UserClient userClient;
    private final JwtHelpers jwtHelpers;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaProducerServiceImp kafkaProducerServiceImp;

    @Autowired
    public AuthService(UserClient userClient, JwtHelpers jwtHelpers, PasswordEncoder passwordEncoder, KafkaTemplate<String, Object> kafkaTemplate, KafkaProducerServiceImp kafkaProducerServiceImp) {
        this.userClient = userClient;
        this.jwtHelpers = jwtHelpers;
        this.passwordEncoder = passwordEncoder;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProducerServiceImp = kafkaProducerServiceImp;
    }

    public TwoFactorResponseDTO initiateAuthentication(UserGinDTO userGinDTO) throws AuthenticationException {
        UserAuthDto userAuthDto = userClient.getUserByEmail(userGinDTO.getCorreo());

        if (userAuthDto == null || !passwordEncoder.matches(userGinDTO.getPassword(), userAuthDto.getPassword())) {
            throw new AuthenticationException("Credenciales inválidas");
        }

        String twoFactorCode = generateTwoFactorCode();
        // Enviar código por Kafka
        EmailDTO emailDTO = new EmailDTO(userGinDTO.getCorreo(), "Verifica tu cuenta", twoFactorCode);
        kafkaProducerServiceImp.sendMessage("new-user", emailDTO);

        // Actualizar el usuario con el código de dos factores
        userClient.updateUserTwoFactorCode(userGinDTO.getCorreo(), twoFactorCode);

        return new TwoFactorResponseDTO(true, "Código de verificación enviado");
    }

    public LoginResponseDTO verifyTwoFactorAndAuthenticate(String email, String code) throws AuthenticationException {
        UserAuthDto userAuthDto = userClient.getUserByEmail(email);

        if (userAuthDto == null || !userAuthDto.getTwoFactorCode().equals(code)) {
            throw new AuthenticationException("Código de verificación inválido");
        }

        String token = jwtHelpers.createTokenWithRole(userAuthDto.getCorreo(), userAuthDto.getRoll());
        Instant expiresAt = Instant.now().plusSeconds(3600); // Token expira en 1 hora

        // Actualizar el usuario con el token y limpiar el código de dos factores
        userClient.updateUserToken(email, token);

        return new LoginResponseDTO(token, userAuthDto.getRoll(), userAuthDto.getCorreo(), expiresAt);
    }

    private String generateTwoFactorCode() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    public LoginResponseDTO refreshToken(String token) throws AuthenticationException {
        if (!jwtHelpers.validateToken(token)) {
            throw new AuthenticationException("Token inválido o expirado");
        }

        String newToken = jwtHelpers.refreshToken(token);
        Claims claims = jwtHelpers.getClaimsFromToken(newToken);
        String email = claims.getSubject();
        String role = claims.get("role", String.class);
        Instant expiresAt = claims.getExpiration().toInstant();

        // Actualizar el token en la base de datos del usuario si es necesario
        userClient.updateUserToken(email, newToken);

        return new LoginResponseDTO(newToken, role, email, expiresAt);
    }
}