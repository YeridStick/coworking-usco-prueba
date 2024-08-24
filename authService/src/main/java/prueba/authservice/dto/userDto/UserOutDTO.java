package prueba.authservice.dto.userDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import prueba.authservice.dto.entidadDto.TipoEntidadEntity;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOutDTO {

    private String numIdent;

    private String nombre;

    private String correo;

    private Long numeroTelefono;

    private TipoEntidadEntity tipoEntidad;

    private String cargoRol;

    //private String mensaje;
}