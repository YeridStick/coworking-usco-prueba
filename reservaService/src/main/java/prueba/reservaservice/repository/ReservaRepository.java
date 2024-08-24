package prueba.reservaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba.reservaservice.entity.ReservaEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    // Método para encontrar reservas por recurso
    List<ReservaEntity> findByRecursoId(Long recursoId);

    // Método para encontrar reservas por usuario
    List<ReservaEntity> findByUsuarioId(String usuarioId);

    // Método para encontrar reservas activas en un rango de tiempo
    List<ReservaEntity> findByFechaInicioBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    // Método para encontrar reservas de un recurso en un rango de tiempo
    List<ReservaEntity> findByRecursoIdAndFechaInicioBetween(Long recursoId, LocalDateTime desde, LocalDateTime hasta);

    // Método para encontrar todas las reservas activas (por ejemplo, estado = "confirmada")
    List<ReservaEntity> findByEstado(String estado);
}