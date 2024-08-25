package prueba.balanceadorcarga.dto.reservaDTO.tipoRecursos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoRecursoResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private Integer cantidadTotal;
    private Integer cantidadDisponible;
}