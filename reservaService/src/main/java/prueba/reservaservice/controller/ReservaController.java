package prueba.reservaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import prueba.reservaservice.dto.ReservaRequestDto;
import prueba.reservaservice.dto.ReservaResponseDto;
import prueba.reservaservice.service.IReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final IReservaService reservaService;

    @Autowired
    public ReservaController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Cualquier usuario autenticado puede crear una reserva
    @PostMapping
    public ResponseEntity<ReservaResponseDto> createReserva(@RequestBody ReservaRequestDto reservaDto) {
        ReservaResponseDto savedReserva = reservaService.createReserva(reservaDto);
        return ResponseEntity.ok(savedReserva);
    }

    // Solo ADMIN puede actualizar una reserva
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> updateReserva(@PathVariable Long id, @RequestBody ReservaRequestDto updatedReservaDto) {
        ReservaResponseDto savedReserva = reservaService.updateReserva(id, updatedReservaDto);
        return ResponseEntity.ok(savedReserva);
    }

    // Cualquier usuario autenticado puede consultar todas las reservas
    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> getAllReservas() {
        List<ReservaResponseDto> reservas = reservaService.getAllReservas();
        return ResponseEntity.ok(reservas);
    }

    // Cualquier usuario autenticado puede consultar una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> getReservaById(@PathVariable Long id) {
        ReservaResponseDto reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }

    // Solo ADMIN puede eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> deleteReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelReserva(id));
    }

    @GetMapping("/correo-reservas")
    public ResponseEntity<List<ReservaResponseDto>> getReservasByEmail(@RequestParam String email) {
        List<ReservaResponseDto> reservas = reservaService.getReservasByEmail(email);
        return ResponseEntity.ok(reservas);
    }

}
