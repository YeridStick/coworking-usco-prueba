package prueba.userservice.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prueba.userservice.dto.in.UserAuthDto;
import prueba.userservice.dto.in.UserCreationDto;
import prueba.userservice.dto.out.UserResponseDto;
import prueba.userservice.entity.RolesEntity;
import prueba.userservice.entity.UserEntity;
import prueba.userservice.exception.ExcepcionPersonalizada;
import prueba.userservice.helpers.JwtHelpers;
import prueba.userservice.repository.RolesRepository;
import prueba.userservice.repository.UserRepository;
import prueba.userservice.services.IUserservice;

import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserservice {

    private final UserRepository userRepository;
    private final JwtHelpers jwtHelpers;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Autowired
    public UserService(UserRepository userRepository, JwtHelpers jwtHelpers, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtHelpers = jwtHelpers;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
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

    @Override
    public UserResponseDto changeUserRole(String userId, String newRole) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RolesEntity role = rolesRepository.findByCargo(newRole)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user.setTipoRoll(role);
        UserEntity updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    @Override
    public UserAuthDto findByEmail(String email) {
        UserEntity user = userRepository.findByCorreo(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el correo: " + email));

        UserAuthDto userAuthDto = modelMapper.map(user, UserAuthDto.class);

        userAuthDto.setRoll(user.getTipoRoll().getIdRoll());

        return userAuthDto;
    }

    public String getUserById(String userId){
        String Correo = "";
        try {
            Optional<UserEntity> use = userRepository.findById(userId);
            if (use.isPresent()) Correo = use.get().getCorreo();
        } catch (Exception e) {
            log.error("Correo no encontrado");
        }
        return Correo;
    }

    @Override
    public void updateTwoFactorCode(String email, String twoFactorCode) {
        UserEntity user = userRepository.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setTwoFactorCode(twoFactorCode);
        userRepository.save(user);
    }
    @Override
    public void updateToken(String email, String token) {
        UserEntity user = userRepository.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setToken(token);
        user.setTwoFactorCode(null); // Limpiar el código de dos factores después de la autenticación
        userRepository.save(user);
    }
}