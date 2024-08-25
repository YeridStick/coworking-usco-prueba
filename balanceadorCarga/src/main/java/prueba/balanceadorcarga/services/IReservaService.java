package prueba.balanceadorcarga.services;

import prueba.balanceadorcarga.dto.reservaDTO.RecursoEntity;
import prueba.balanceadorcarga.dto.reservaDTO.ReservaRequestDto;
import prueba.balanceadorcarga.dto.reservaDTO.ReservaResponseDto;
import prueba.balanceadorcarga.dto.reservaDTO.TipoRecursoEntity;
import prueba.balanceadorcarga.dto.reservaDTO.horarioDto.RecursoDisponibilidadDto;
import prueba.balanceadorcarga.dto.reservaDTO.recursos.RecursoResponseDto;
import prueba.balanceadorcarga.dto.reservaDTO.tipoRecursos.TipoRecursoRequestDto;
import prueba.balanceadorcarga.dto.reservaDTO.tipoRecursos.TipoRecursoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservaService {

    ReservaResponseDto createReserva(ReservaRequestDto reservaDto);

    ReservaResponseDto updateReserva(Long id, ReservaRequestDto updatedReservaDto);

    List<ReservaResponseDto> getAllReservas();

    ReservaResponseDto getReservaById(Long id);

    ReservaResponseDto deleteReserva(Long id);

    // Recursos
    List<RecursoResponseDto> getAllRecursos();

    RecursoResponseDto getRecursoById(Long id);

    void deleteRecurso(Long id);

    RecursoDisponibilidadDto getRecursoDisponibilidad(Long recursoId, LocalDateTime desde, LocalDateTime hasta);

    void updateRecursoEstado(Long id, RecursoEntity.EstadoRecurso nuevoEstado);

    List<RecursoEntity.EstadoRecurso> getAllEstadosRecurso();

    List<RecursoResponseDto> getRecursosPorTipo(Long tipoRecursoId);

    String getEstadoRecurso(Long recursoId);

    // Tipo de Reserva
    TipoRecursoResponseDto createTipoRecurso(TipoRecursoRequestDto tipoRecursoDto);

    TipoRecursoResponseDto updateTipoRecurso(Long id, TipoRecursoRequestDto tipoRecursoDto);

    TipoRecursoResponseDto getTipoRecursoById(Long id);

    List<TipoRecursoResponseDto> getAllTiposRecurso();

    void deleteTipoRecurso(Long id);

    List<TipoRecursoEntity.CategoriaRecurso> getAllCategorias();

    TipoRecursoResponseDto updateTipoRecursoCategoria(Long id, TipoRecursoEntity.CategoriaRecurso nuevaCategoria);

    // Categoría
    List<String> getCategorias();
}