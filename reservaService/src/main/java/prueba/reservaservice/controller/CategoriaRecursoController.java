package prueba.reservaservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prueba.reservaservice.entity.TipoRecursoEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias-recurso")
public class CategoriaRecursoController {

    // Permitir acceso a cualquier usuario autenticado
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<String>> getCategorias() {
        List<String> categorias = Arrays.stream(TipoRecursoEntity.CategoriaRecurso.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }
}
