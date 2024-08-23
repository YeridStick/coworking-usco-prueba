package prueba.userservice.servicesImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prueba.userservice.dto.in.UserCreationDto;
import prueba.userservice.dto.out.UserResponseDto;
import prueba.userservice.entity.RolesEntity;
import prueba.userservice.entity.UserEntity;
import prueba.userservice.repository.RolesRepository;
import prueba.userservice.repository.UserRepository;
import prueba.userservice.services.IUserservice;

@Service
public class UserService implements IUserservice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDto createUser(UserCreationDto userDto) {
        if (userRepository.findByCorreo(userDto.getCorreo()).isPresent()) {
            throw new RuntimeException("El usuario con este correo ya existe");
        }

        RolesEntity userRole = rolesRepository.findByCargo("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        UserEntity newUser = modelMapper.map(userDto, UserEntity.class);

        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setTipoRoll(userRole);

        UserEntity savedUser = userRepository.save(newUser);

        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    public UserResponseDto changeUserRole(String userId, String newRole) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RolesEntity role = rolesRepository.findByCargo(newRole)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user.setTipoRoll(role);
        UserEntity updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponseDto.class);
    }
}