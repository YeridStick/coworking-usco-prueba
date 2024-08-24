package prueba.reservaservice.dto.tipoRecursos;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoRecursoRequestDto {
    private String nombre;
    private String descripcion;
    private String categoria;
    private Integer cantidadTotal;
    private Integer cantidadDisponible;
}