package prueba.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.userservice.dto.in.UserAuthDto;
import prueba.userservice.dto.in.UserCreationDto;
import prueba.userservice.dto.out.UserDto;
import prueba.userservice.dto.out.UserResponseDto;
import prueba.userservice.entity.UserEntity;
import prueba.userservice.services.IUserservice;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuario")
@Slf4j
public class UserController {

    private final IUserservice userService;

    @Autowired
    public UserController(IUserservice userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/agregar")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreationDto userDto) {
        UserResponseDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserResponseDto> changeUserRole(@PathVariable String userId, @RequestParam String newRole) {
        UserResponseDto updatedUser = userService.changeUserRole(userId, newRole);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserAuthDto> getByEmail(@PathVariable String email) {
        UserAuthDto user =  userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }
}