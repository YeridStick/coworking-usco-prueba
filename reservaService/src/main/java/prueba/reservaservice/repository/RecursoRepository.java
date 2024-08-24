package prueba.reservaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prueba.reservaservice.entity.RecursoEntity;

import java.util.List;

public interface RecursoRepository extends JpaRepository<RecursoEntity, Long> {
    List<RecursoEntity> findByTipoRecursoId(Long tipoRecursoId);
}
