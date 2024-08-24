package prueba.reservaservice.dto.recursos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecursoRequestDto {
    private Long tipoRecursoId;
    private String codigoIdentificacion;
    private String estado;
}