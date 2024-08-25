package prueba.balanceadorcarga.services;

import org.springframework.http.ResponseEntity;
import prueba.balanceadorcarga.dto.userDto.in.UserAuthDto;
import prueba.balanceadorcarga.dto.userDto.in.UserCreationDto;
import prueba.balanceadorcarga.dto.userDto.out.UserResponseDto;

public interface IUserServices {

    ResponseEntity<String> printUserById(String id);

    ResponseEntity<UserAuthDto> printUserByEmail(String email);

    ResponseEntity<UserResponseDto> createUser(UserCreationDto userCreationDto);

    ResponseEntity<UserResponseDto> changeUserRole(String userId, String newRole);

    ResponseEntity<String> printRoleCargo(String idRol);

    ResponseEntity<Void> updateTwoFactorCode(String email, String twoFactorCode);

    ResponseEntity<Void> updateToken(String email, String token);
}
