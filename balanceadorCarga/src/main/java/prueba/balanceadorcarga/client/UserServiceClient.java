package prueba.balanceadorcarga.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.balanceadorcarga.dto.userDto.in.UserAuthDto;
import prueba.balanceadorcarga.dto.userDto.in.UserCreationDto;
import prueba.balanceadorcarga.dto.userDto.out.UserResponseDto;

@FeignClient(name = "userService")
public interface UserServiceClient {

    @PostMapping("/api/user/agregar")
    ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreationDto userDto);


    @PutMapping("/api/user/{userId}/role")
    ResponseEntity<UserResponseDto> changeUserRole(@PathVariable String userId, @RequestParam String newRole);

    @GetMapping("/api/user/email/{email}")
    ResponseEntity<UserAuthDto> getByEmail(@PathVariable String email);

    @GetMapping("/api/user/{id}")
    String getUserById(@PathVariable String id);

    @PutMapping("/api/user/update-two-factor-code")
    ResponseEntity<Void> updateTwoFactorCode(@RequestParam String email, @RequestParam String twoFactorCode);

    @PutMapping("/api/user/update-token")
    ResponseEntity<Void> updateToken(@RequestParam String email, @RequestParam String token);

    @GetMapping("/api/role/id/{idRol}")
    ResponseEntity<String> getRoleCargo(@PathVariable String idRol);
}