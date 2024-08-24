package prueba.authservice.dto.entidadDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoEntidadEntity {
    private Long idEntidad;
    private String sigla;
    private String nombreEntidad;
}
