package prueba.reservaservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRequestDto {
    private String usuarioId;
    private Long recursoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}