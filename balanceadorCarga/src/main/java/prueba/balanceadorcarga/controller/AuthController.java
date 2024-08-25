package prueba.balanceadorcarga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.balanceadorcarga.dto.auth.LoginResponseDTO;
import prueba.balanceadorcarga.dto.auth.autenticacion.TwoFactorResponseDTO;
import prueba.balanceadorcarga.dto.auth.userDto.UserGinDTO;
import prueba.balanceadorcarga.services.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TwoFactorResponseDTO> login(@RequestBody UserGinDTO userGinDTO) {
        TwoFactorResponseDTO response = authService.login(userGinDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<LoginResponseDTO> verifyTwoFactor(@RequestParam String email, @RequestParam String code) {
        LoginResponseDTO response = authService.verifyTwoFactor(email, code);
        return ResponseEntity.ok(response);
    }
}