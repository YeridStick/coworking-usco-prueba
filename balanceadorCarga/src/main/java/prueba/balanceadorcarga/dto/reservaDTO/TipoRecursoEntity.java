package prueba.balanceadorcarga.dto.reservaDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TipoRecursoEntity {

    private Long id;
    private String nombre;
    private String descripcion;
    private CategoriaRecurso categoria;
    private Integer cantidadTotal;
    private Integer cantidadDisponible;
    private List<RecursoEntity> recursos = new ArrayList<>();

    public enum CategoriaRecurso {
        SALA, EQUIPAMIENTO
    }
}