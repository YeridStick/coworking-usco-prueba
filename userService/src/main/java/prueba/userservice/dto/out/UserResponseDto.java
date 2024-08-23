package prueba.userservice.dto.out;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private String numIdent;
    private String nombre;
    private String correo;
    private String tipoRoll;
    private LocalDateTime fechaCreacion;
}