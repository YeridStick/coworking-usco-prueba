package prueba.balanceadorcarga.dto.reservaDTO.horarioDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecursoDisponibilidadDto {
    private Long recursoId;
    private String codigoIdentificacion;
    private Long tipoRecursoId;
    private String nombreTipoRecurso;
    private String estado;
    private List<ReservaInfoDto> reservas;

    @Data
    public static class ReservaInfoDto {
        private Long reservaId;
        private String usuarioId;
        private String correoUsuario;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaFin;
    }
}