package prueba.reservaservice.service;

import prueba.reservaservice.dto.tipoRecursos.TipoRecursoRequestDto;
import prueba.reservaservice.dto.tipoRecursos.TipoRecursoResponseDto;
import prueba.reservaservice.entity.TipoRecursoEntity;

import java.util.List;

public interface ITipoRecursoService {
    TipoRecursoResponseDto createTipoRecurso(TipoRecursoRequestDto tipoRecursoDto);
    TipoRecursoResponseDto updateTipoRecurso(Long id, TipoRecursoRequestDto tipoRecursoDto);
    TipoRecursoResponseDto getTipoRecursoById(Long id);
    List<TipoRecursoResponseDto> getAllTiposRecurso();
    void deleteTipoRecurso(Long id);
    TipoRecursoResponseDto updateTipoRecursoCategoria(Long id, TipoRecursoEntity.CategoriaRecurso nuevaCategoria);
}
