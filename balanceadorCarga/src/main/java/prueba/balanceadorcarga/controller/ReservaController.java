package prueba.balanceadorcarga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prueba.balanceadorcarga.dto.reservaDTO.RecursoEntity;
import prueba.balanceadorcarga.dto.reservaDTO.ReservaRequestDto;
import prueba.balanceadorcarga.dto.reservaDTO.ReservaResponseDto;
import prueba.balanceadorcarga.dto.reservaDTO.TipoRecursoEntity;
import prueba.balanceadorcarga.dto.reservaDTO.horarioDto.RecursoDisponibilidadDto;
import prueba.balanceadorcarga.dto.reservaDTO.recursos.RecursoResponseDto;
import prueba.balanceadorcarga.dto.reservaDTO.tipoRecursos.TipoRecursoRequestDto;
import prueba.balanceadorcarga.dto.reservaDTO.tipoRecursos.TipoRecursoResponseDto;
import prueba.balanceadorcarga.services.IReservaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final IReservaService reservaService;

    @Autowired
    public ReservaController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Reservas
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDto> createReserva(@RequestBody ReservaRequestDto reservaDto) {
        ReservaResponseDto response = reservaService.createReserva(reservaDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDto> updateReserva(@PathVariable Long id, @RequestBody ReservaRequestDto updatedReservaDto) {
        ReservaResponseDto response = reservaService.updateReserva(id, updatedReservaDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> getAllReservas() {
        List<ReservaResponseDto> response = reservaService.getAllReservas();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> getReservaById(@PathVariable Long id) {
        ReservaResponseDto response = reservaService.getReservaById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> deleteReserva(@PathVariable Long id) {
        ReservaResponseDto response = reservaService.deleteReserva(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/correo-reservas")
    public ResponseEntity<List<ReservaResponseDto>> getReservasByEmail(@RequestParam String email) {
        List<ReservaResponseDto> reservas = reservaService.getReservasByEmail(email);
        return ResponseEntity.ok(reservas);
    }

    // Recursos
    @GetMapping("/recursos")
    public ResponseEntity<List<RecursoResponseDto>> getAllRecursos() {
        List<RecursoResponseDto> response = reservaService.getAllRecursos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recursos/{id}")
    public ResponseEntity<RecursoResponseDto> getRecursoById(@PathVariable Long id) {
        RecursoResponseDto response = reservaService.getRecursoById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/recursos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRecurso(@PathVariable Long id) {
        reservaService.deleteRecurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recursos/{recursoId}/disponibilidad")
    public ResponseEntity<RecursoDisponibilidadDto> getRecursoDisponibilidad(
            @PathVariable Long recursoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        RecursoDisponibilidadDto response = reservaService.getRecursoDisponibilidad(recursoId, desde, hasta);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/recursos/{id}/estado/{nuevoEstado}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateRecursoEstado(
            @PathVariable Long id,
            @PathVariable RecursoEntity.EstadoRecurso nuevoEstado) {
        reservaService.updateRecursoEstado(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recursos/estados")
    public ResponseEntity<List<RecursoEntity.EstadoRecurso>> getAllEstadosRecurso() {
        List<RecursoEntity.EstadoRecurso> response = reservaService.getAllEstadosRecurso();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recursos/por-tipo/{tipoRecursoId}")
    public ResponseEntity<List<RecursoResponseDto>> getRecursosPorTipo(@PathVariable Long tipoRecursoId) {
        List<RecursoResponseDto> response = reservaService.getRecursosPorTipo(tipoRecursoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recursos/{recursoId}/estado")
    public ResponseEntity<String> getEstadoRecurso(@PathVariable Long recursoId) {
        String response = reservaService.getEstadoRecurso(recursoId);
        return ResponseEntity.ok(response);
    }

    // Tipos de Recurso
    @PostMapping("/tipos-recurso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoRecursoResponseDto> createTipoRecurso(@RequestBody TipoRecursoRequestDto tipoRecursoDto) {
        TipoRecursoResponseDto response = reservaService.createTipoRecurso(tipoRecursoDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/tipos-recurso/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoRecursoResponseDto> updateTipoRecurso(@PathVariable Long id, @RequestBody TipoRecursoRequestDto tipoRecursoDto) {
        TipoRecursoResponseDto response = reservaService.updateTipoRecurso(id, tipoRecursoDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipos-recurso/{id}")
    public ResponseEntity<TipoRecursoResponseDto> getTipoRecursoById(@PathVariable Long id) {
        TipoRecursoResponseDto response = reservaService.getTipoRecursoById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipos-recurso")
    public ResponseEntity<List<TipoRecursoResponseDto>> getAllTiposRecurso() {
        List<TipoRecursoResponseDto> response = reservaService.getAllTiposRecurso();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tipos-recurso/{id}")
    public ResponseEntity<Void> deleteTipoRecurso(@PathVariable Long id) {
        reservaService.deleteTipoRecurso(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipos-recurso/categorias")
    public ResponseEntity<List<TipoRecursoEntity.CategoriaRecurso>> getAllCategorias() {
        List<TipoRecursoEntity.CategoriaRecurso> response = reservaService.getAllCategorias();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/tipos-recurso/{id}/categoria")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoRecursoResponseDto> updateTipoRecursoCategoria(
            @PathVariable Long id,
            @RequestParam TipoRecursoEntity.CategoriaRecurso nuevaCategoria) {
        TipoRecursoResponseDto response = reservaService.updateTipoRecursoCategoria(id, nuevaCategoria);
        return ResponseEntity.ok(response);
    }

    // Categorías
    @GetMapping("/categorias-recurso")
    public ResponseEntity<List<String>> getCategorias() {
        List<String> response = reservaService.getCategorias();
        return ResponseEntity.ok(response);
    }
}