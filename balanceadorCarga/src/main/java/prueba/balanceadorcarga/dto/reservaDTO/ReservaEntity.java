package prueba.balanceadorcarga.dto.reservaDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEntity {
    private Long id;
    private String usuarioId;
    private RecursoEntity recurso;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
}
