package prueba.reservaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prueba.reservaservice.dto.tipoRecursos.TipoRecursoRequestDto;
import prueba.reservaservice.dto.tipoRecursos.TipoRecursoResponseDto;
import prueba.reservaservice.entity.TipoRecursoEntity;
import prueba.reservaservice.service.ITipoRecursoService;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-recurso")
public class TipoRecursoController {

    private final ITipoRecursoService tipoRecursoService;

    @Autowired
    public TipoRecursoController(ITipoRecursoService tipoRecursoService) {
        this.tipoRecursoService = tipoRecursoService;
    }

    // Solo ADMIN puede crear un nuevo tipo de recurso
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoRecursoResponseDto> createTipoRecurso(@RequestBody TipoRecursoRequestDto tipoRecursoDto) {
        TipoRecursoResponseDto createdTipoRecurso = tipoRecursoService.createTipoRecurso(tipoRecursoDto);
        return ResponseEntity.ok(createdTipoRecurso);
    }

    // Solo ADMIN puede actualizar un tipo de recurso
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoRecursoResponseDto> updateTipoRecurso(@PathVariable Long id, @RequestBody TipoRecursoRequestDto tipoRecursoDto) {
        TipoRecursoResponseDto updatedTipoRecurso = tipoRecursoService.updateTipoRecurso(id, tipoRecursoDto);
        return ResponseEntity.ok(updatedTipoRecurso);
    }

    // Cualquier usuario autenticado puede consultar un tipo de recurso por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoRecursoResponseDto> getTipoRecursoById(@PathVariable Long id) {
        TipoRecursoResponseDto tipoRecurso = tipoRecursoService.getTipoRecursoById(id);
        return ResponseEntity.ok(tipoRecurso);
    }

    // Cualquier usuario autenticado puede obtener la lista de todos los tipos de recursos
    @GetMapping
    public ResponseEntity<List<TipoRecursoResponseDto>> getAllTiposRecurso() {
        List<TipoRecursoResponseDto> tiposRecurso = tipoRecursoService.getAllTiposRecurso();
        return ResponseEntity.ok(tiposRecurso);
    }

    // Solo ADMIN puede eliminar un tipo de recurso
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTipoRecurso(@PathVariable Long id) {
        tipoRecursoService.deleteTipoRecurso(id);
        return ResponseEntity.noContent().build();
    }

    // Cualquier usuario autenticado puede obtener la lista de categorías
    @GetMapping("/categorias")
    public ResponseEntity<List<TipoRecursoEntity.CategoriaRecurso>> getAllCategorias() {
        List<TipoRecursoEntity.CategoriaRecurso> categorias = List.of(TipoRecursoEntity.CategoriaRecurso.values());
        return ResponseEntity.ok(categorias);
    }

    // Solo ADMIN puede actualizar la categoría de un tipo de recurso
    @PatchMapping("/{id}/categoria")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoRecursoResponseDto> updateTipoRecursoCategoria(
            @PathVariable Long id,
            @RequestParam TipoRecursoEntity.CategoriaRecurso nuevaCategoria) {
        TipoRecursoResponseDto updatedTipoRecurso = tipoRecursoService.updateTipoRecursoCategoria(id, nuevaCategoria);
        return ResponseEntity.ok(updatedTipoRecurso);
    }
}
