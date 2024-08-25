package prueba.balanceadorcarga.dto.reservaDTO.recursos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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