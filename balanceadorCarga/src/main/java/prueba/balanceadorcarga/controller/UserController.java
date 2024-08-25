package prueba.balanceadorcarga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prueba.balanceadorcarga.dto.userDto.in.UserAuthDto;
import prueba.balanceadorcarga.dto.userDto.in.UserCreationDto;
import prueba.balanceadorcarga.dto.userDto.out.UserResponseDto;
import prueba.balanceadorcarga.services.IUserServices;

@RestController
@RequestMapping("/api/balanceador")
public class UserController {

    @Autowired
    private IUserServices userServices;

    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUserById(@PathVariable String id) {
        return userServices.printUserById(id);
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserAuthDto> getUserByEmail(@PathVariable String email) {
        return userServices.printUserByEmail(email);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreationDto userCreationDto) {
        return userServices.createUser(userCreationDto);
    }

    @PutMapping("/user/{userId}/role")
    public ResponseEntity<UserResponseDto> changeUserRole(@PathVariable String userId, @RequestParam String newRole) {
        return userServices.changeUserRole(userId, newRole);
    }

    @GetMapping("/role/{idRol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getRoleCargo(@PathVariable String idRol) {
        return userServices.printRoleCargo(idRol);
    }

    @PutMapping("/user/update-two-factor-code")
    public ResponseEntity<Void> updateTwoFactorCode(@RequestParam String email, @RequestParam String twoFactorCode) {
        return userServices.updateTwoFactorCode(email, twoFactorCode);
    }

    @PutMapping("/user/update-token")
    public ResponseEntity<Void> updateToken(@RequestParam String email, @RequestParam String token) {
        return userServices.updateToken(email, token);
    }
}