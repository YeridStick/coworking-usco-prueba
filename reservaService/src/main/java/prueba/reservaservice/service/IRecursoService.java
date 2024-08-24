package prueba.reservaservice.service;

import prueba.reservaservice.dto.horarioDto.RecursoDisponibilidadDto;
import prueba.reservaservice.dto.recursos.RecursoRequestDto;
import prueba.reservaservice.dto.recursos.RecursoResponseDto;
import prueba.reservaservice.entity.RecursoEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IRecursoService {
    RecursoEntity createRecurso(RecursoRequestDto recursoDto);
    RecursoResponseDto updateRecurso(Long id, RecursoRequestDto recursoDto);
    List<RecursoResponseDto> getAllRecursos();
    RecursoResponseDto getRecursoById(Long id);
    void deleteRecurso(Long id);
    RecursoDisponibilidadDto getRecursoDisponibilidad(Long recursoId, LocalDateTime desde, LocalDateTime hasta);
    RecursoResponseDto updateRecursoEstado(Long recursoId, RecursoEntity.EstadoRecurso nuevoEstado);
    List<RecursoResponseDto> getRecursosPorTipo(Long tipoRecursoId);
    String getEstadoRecurso(Long recursoId);
}
