package prueba.balanceadorcarga.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.balanceadorcarga.dto.auth.LoginResponseDTO;
import prueba.balanceadorcarga.dto.auth.autenticacion.TwoFactorResponseDTO;
import prueba.balanceadorcarga.dto.auth.userDto.UserGinDTO;

@FeignClient(name = "authService")
public interface AuthClient {

    @PostMapping("/auth/login")
    ResponseEntity<TwoFactorResponseDTO> login(@RequestBody UserGinDTO userGinDTO);

    @PostMapping("/auth/verify-2fa")
    ResponseEntity<LoginResponseDTO> verifyTwoFactor(@RequestParam String email, @RequestParam String code);

    @PostMapping("/auth/refresh-token")
    ResponseEntity<LoginResponseDTO> refreshToken(@RequestHeader("Authorization") String authHeader);
}
