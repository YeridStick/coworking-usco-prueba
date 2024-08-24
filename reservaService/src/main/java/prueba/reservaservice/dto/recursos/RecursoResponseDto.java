package prueba.reservaservice.dto.recursos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecursoResponseDto {
    private Long id;
    private Long tipoRecursoId;
    private String tipoRecursoNombre;
    private String codigoIdentificacion;
    private String estado;
}