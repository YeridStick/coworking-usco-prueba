package prueba.balanceadorcarga.dto.reservaDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecursoEntity {
    private Long id;
    private TipoRecursoEntity tipoRecurso;
    private String codigoIdentificacion;
    private EstadoRecurso estado = EstadoRecurso.DISPONIBLE;
    private List<ReservaEntity> reservas = new ArrayList<>();

    public enum EstadoRecurso {
        DISPONIBLE, EN_MANTENIMIENTO, FUERA_DE_SERVICIO, RESERVADO
    }
}