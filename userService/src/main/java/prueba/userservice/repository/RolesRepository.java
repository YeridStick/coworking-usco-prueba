package prueba.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba.userservice.entity.RolesEntity;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, String> {
    Optional<RolesEntity> findByCargo(String cargo);
    Optional<RolesEntity> findByIdRoll(String Idcargo);
}