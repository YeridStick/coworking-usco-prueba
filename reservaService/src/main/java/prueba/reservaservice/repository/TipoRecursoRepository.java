package prueba.reservaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prueba.reservaservice.entity.TipoRecursoEntity;

public interface TipoRecursoRepository extends JpaRepository<TipoRecursoEntity, Long> {
}
