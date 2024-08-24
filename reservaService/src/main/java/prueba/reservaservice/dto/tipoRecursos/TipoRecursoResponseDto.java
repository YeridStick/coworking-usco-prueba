package prueba.reservaservice.dto.tipoRecursos;

import lombok.*;

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