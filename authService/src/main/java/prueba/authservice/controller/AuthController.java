package prueba.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.authservice.dto.LoginResponseDTO;
import prueba.authservice.dto.autenticacion.TwoFactorResponseDTO;
import prueba.authservice.dto.userDto.UserGinDTO;
import prueba.authservice.exception.ExcepcionPersonalizada;
import prueba.authservice.service.imp.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserGinDTO userGinDTO) {
        try {
            TwoFactorResponseDTO response = authService.initiateAuthentication(userGinDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error de login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<LoginResponseDTO> verifyTwoFactor(@RequestParam String email, @RequestParam String code) {
        try {
            LoginResponseDTO response = authService.verifyTwoFactorAndAuthenticate(email, code);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error en la verificación de dos factores: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}