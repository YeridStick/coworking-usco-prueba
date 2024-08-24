package prueba.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.userservice.dto.in.UserCreationDto;
import prueba.userservice.dto.out.UserResponseDto;
import prueba.userservice.services.IRollServices;

@RestController
@RequestMapping("/api/role")
@Tag(name = "Roles")
@Slf4j
public class RollController {
    private final IRollServices rollServices;

    public RollController(IRollServices rollServices) {
        this.rollServices = rollServices;
    }

    @Operation(summary = "burcar rol por Id")
    @GetMapping("/id/{idRol}")
    public ResponseEntity<String> createUser(@PathVariable String idRol) {
        return ResponseEntity.ok(rollServices.getCargo(idRol));
    }
}
