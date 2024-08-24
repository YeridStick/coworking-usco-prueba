package prueba.reservaservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDto {
    private Long id;
    private String usuarioId;
    private Long recursoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}