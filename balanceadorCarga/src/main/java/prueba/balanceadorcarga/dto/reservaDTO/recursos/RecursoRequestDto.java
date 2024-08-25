package prueba.balanceadorcarga.dto.reservaDTO.recursos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecursoRequestDto {
    private Long tipoRecursoId;
    private String codigoIdentificacion;
    private String estado;
}