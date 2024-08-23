package prueba.userservice.services;

import prueba.userservice.dto.in.UserCreationDto;
import prueba.userservice.dto.out.UserResponseDto;
import prueba.userservice.entity.UserEntity;

public interface IUserservice {
    UserResponseDto createUser(UserCreationDto userDto);
    UserResponseDto changeUserRole(String userId, String newRole);
}
