package prueba.authservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prueba.authservice.dto.LoginResponseDTO;
import prueba.authservice.dto.userDto.UserGinDTO;
import prueba.authservice.exception.ExcepcionPersonalizada;
import prueba.authservice.service.imp.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserGinDTO userGinDTO) {
        try {
            return ResponseEntity.ok(userService.authenticate(userGinDTO));
        } catch (Exception e) {
            throw new ExcepcionPersonalizada("Error de login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}