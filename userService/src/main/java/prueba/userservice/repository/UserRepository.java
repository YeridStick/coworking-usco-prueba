package prueba.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prueba.userservice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByCorreo(String correo);
}