package prueba.reservaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prueba.reservaservice.dto.horarioDto.RecursoDisponibilidadDto;
import prueba.reservaservice.dto.recursos.RecursoResponseDto;
import prueba.reservaservice.entity.RecursoEntity;
import prueba.reservaservice.service.IRecursoService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final IRecursoService recursoService;

    @Autowired
    public RecursoController(IRecursoService recursoService) {
        this.recursoService = recursoService;
    }

    // Cualquier usuario autenticado puede consultar todos los recursos
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RecursoResponseDto>> getAllRecursos() {
        List<RecursoResponseDto> recursos = recursoService.getAllRecursos();
        return ResponseEntity.ok(recursos);
    }

    // Cualquier usuario autenticado puede consultar un recurso por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<RecursoResponseDto> getRecursoById(@PathVariable Long id) {
        RecursoResponseDto recurso = recursoService.getRecursoById(id);
        return ResponseEntity.ok(recurso);
    }

    // Solo ADMIN puede eliminar un recurso
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRecurso(@PathVariable Long id) {
        recursoService.deleteRecurso(id);
        return ResponseEntity.noContent().build();
    }

    // Cualquier usuario autenticado puede consultar la disponibilidad de un recurso
    @GetMapping("/{recursoId}/disponibilidad")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<RecursoDisponibilidadDto> getRecursoDisponibilidad(
            @PathVariable Long recursoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {

        RecursoDisponibilidadDto disponibilidad = recursoService.getRecursoDisponibilidad(recursoId, desde, hasta);
        return ResponseEntity.ok(disponibilidad);
    }

    // Solo ADMIN puede actualizar el estado de un recurso es el unico dato editable
    // (los demas vienes generados sistemamente)
    @PatchMapping("/{id}/estado/{nuevoEstado}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRecursoEstado(
            @PathVariable Long id,
            @PathVariable RecursoEntity.EstadoRecurso nuevoEstado) {
        try {
            RecursoResponseDto updatedRecurso = recursoService.updateRecursoEstado(id, nuevoEstado);
            return ResponseEntity.ok(updatedRecurso);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estado del recurso");
        }
    }

    // Cualquier usuario autenticado puede obtener todos los posibles estados de un recurso
    @GetMapping("/estados")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<RecursoEntity.EstadoRecurso>> getAllEstadosRecurso() {
        List<RecursoEntity.EstadoRecurso> estados = List.of(RecursoEntity.EstadoRecurso.values());
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/por-tipo/{tipoRecursoId}")
    public ResponseEntity<List<RecursoResponseDto>> getRecursosPorTipo(@PathVariable Long tipoRecursoId) {
        List<RecursoResponseDto> recursos = recursoService.getRecursosPorTipo(tipoRecursoId);
        return ResponseEntity.ok(recursos);
    }
    @GetMapping("/{recursoId}/estado")
    public ResponseEntity<String> getEstadoRecurso(@PathVariable Long recursoId) {
        String estado = recursoService.getEstadoRecurso(recursoId);
        return ResponseEntity.ok(estado);
    }
}
