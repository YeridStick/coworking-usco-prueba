package prueba.balanceadorcarga.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prueba.balanceadorcarga.client.AuthClient;
import prueba.balanceadorcarga.dto.auth.LoginResponseDTO;
import prueba.balanceadorcarga.dto.auth.autenticacion.TwoFactorResponseDTO;
import prueba.balanceadorcarga.dto.auth.userDto.UserGinDTO;
import prueba.balanceadorcarga.services.IAuthService;

@Service
public class AuthService implements IAuthService {

    private final AuthClient authClient;

    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public TwoFactorResponseDTO login(UserGinDTO userGinDTO) {
        ResponseEntity<TwoFactorResponseDTO> response = authClient.login(userGinDTO);
        return response.getBody();
    }

    @Override
    public LoginResponseDTO verifyTwoFactor(String email, String code) {
        ResponseEntity<LoginResponseDTO> response = authClient.verifyTwoFactor(email, code);
        return response.getBody();
    }

    @Override
    public LoginResponseDTO refreshToken(String authHeader) {
        ResponseEntity<LoginResponseDTO> response = authClient.refreshToken(authHeader);
        return response.getBody();
    }
}
