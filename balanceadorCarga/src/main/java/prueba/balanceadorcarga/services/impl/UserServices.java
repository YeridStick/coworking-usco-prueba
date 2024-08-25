package prueba.balanceadorcarga.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prueba.balanceadorcarga.client.UserServiceClient;
import prueba.balanceadorcarga.dto.userDto.in.UserAuthDto;
import prueba.balanceadorcarga.dto.userDto.in.UserCreationDto;
import prueba.balanceadorcarga.dto.userDto.out.UserResponseDto;
import prueba.balanceadorcarga.services.IUserServices;

@Service
public class UserServices implements IUserServices {

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public ResponseEntity<String> printUserById(String id) {
        return ResponseEntity.ok(userServiceClient.getUserById(id));
    }

    @Override
    public ResponseEntity<UserAuthDto> printUserByEmail(String email) {
        return userServiceClient.getByEmail(email);
    }

    @Override
    public ResponseEntity<UserResponseDto> createUser(UserCreationDto userCreationDto) {
        return userServiceClient.createUser(userCreationDto);
    }

    @Override
    public ResponseEntity<UserResponseDto> changeUserRole(String userId, String newRole) {
        return userServiceClient.changeUserRole(userId, newRole);
    }

    @Override
    public ResponseEntity<String> printRoleCargo(String idRol) {
        return userServiceClient.getRoleCargo(idRol);
    }

    @Override
    public ResponseEntity<Void> updateTwoFactorCode(String email, String twoFactorCode) {
        return userServiceClient.updateTwoFactorCode(email, twoFactorCode);
    }

    @Override
    public ResponseEntity<Void> updateToken(String email, String token) {
        return userServiceClient.updateToken(email, token);
    }
}
