package prueba.balanceadorcarga.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

@FeignClient(name = "reservaService")
public interface ReservaClient {

    @PostMapping("/api/reservas")
    ResponseEntity<ReservaResponseDto> createReserva(@RequestBody ReservaRequestDto reservaDto);

    @PutMapping("/api/reservas/{id}")
    ResponseEntity<ReservaResponseDto> updateReserva(@PathVariable Long id, @RequestBody ReservaRequestDto updatedReservaDto);

    @GetMapping("/api/reservas")
    ResponseEntity<List<ReservaResponseDto>> getAllReservas();

    @GetMapping("/api/reservas/{id}")
    ResponseEntity<ReservaResponseDto> getReservaById(@PathVariable Long id);

    @DeleteMapping("/api/reservas/{id}")
    ResponseEntity<ReservaResponseDto> deleteReserva(@PathVariable Long id);

    // recursos

    @GetMapping("/api/recursos")
    ResponseEntity<List<RecursoResponseDto>> getAllRecursos();

    @GetMapping("/api/recursos/{id}")
    ResponseEntity<RecursoResponseDto> getRecursoById(@PathVariable Long id);

    @DeleteMapping("/api/recursos/{id}")
    ResponseEntity<Void> deleteRecurso(@PathVariable Long id);

    @GetMapping("/api/recursos/{recursoId}/disponibilidad")
    ResponseEntity<RecursoDisponibilidadDto> getRecursoDisponibilidad(
            @PathVariable Long recursoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta);

    @PatchMapping("/api/recursos/{id}/estado/{nuevoEstado}")
    ResponseEntity<?> updateRecursoEstado(
            @PathVariable Long id,
            @PathVariable RecursoEntity.EstadoRecurso nuevoEstado);

    @GetMapping("/api/recursos/estados")
    ResponseEntity<List<RecursoEntity.EstadoRecurso>> getAllEstadosRecurso();

    @GetMapping("/api/recursos/por-tipo/{tipoRecursoId}")
    ResponseEntity<List<RecursoResponseDto>> getRecursosPorTipo(@PathVariable Long tipoRecursoId);

    @GetMapping("/api/recursos/{recursoId}/estado")
    ResponseEntity<String> getEstadoRecurso(@PathVariable Long recursoId);

    // tipo de reserva
    @PostMapping("/api/tipos-recurso")
    ResponseEntity<TipoRecursoResponseDto> createTipoRecurso(@RequestBody TipoRecursoRequestDto tipoRecursoDto);

    @PutMapping("/api/tipos-recurso/{id}")
    ResponseEntity<TipoRecursoResponseDto> updateTipoRecurso(@PathVariable Long id, @RequestBody TipoRecursoRequestDto tipoRecursoDto);

    @GetMapping("/api/tipos-recurso/{id}")
    ResponseEntity<TipoRecursoResponseDto> getTipoRecursoById(@PathVariable Long id);

    @GetMapping("/api/tipos-recurso")
    ResponseEntity<List<TipoRecursoResponseDto>> getAllTiposRecurso();

    @DeleteMapping("/api/tipos-recurso/{id}")
    ResponseEntity<Void> deleteTipoRecurso(@PathVariable Long id);

    @GetMapping("/api/tipos-recurso/categorias")
    ResponseEntity<List<TipoRecursoEntity.CategoriaRecurso>> getAllCategorias();

    @PatchMapping("/api/tipos-recurso/{id}/categoria")
    ResponseEntity<TipoRecursoResponseDto> updateTipoRecursoCategoria(
            @PathVariable Long id,
            @RequestParam TipoRecursoEntity.CategoriaRecurso nuevaCategoria);

    // Categoria
    @GetMapping("/api/categorias-recurso")
    ResponseEntity<List<String>> getCategorias();
}