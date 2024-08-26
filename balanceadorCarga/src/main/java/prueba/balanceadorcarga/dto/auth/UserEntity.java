package prueba.balanceadorcarga.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prueba.balanceadorcarga.dto.auth.userDto.RolesEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String numIdent;
    private String nombre;
    private String correo;
    private String password;
    private String twoFactorCode;
    private String token;
    private RolesEntity tipoRoll;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
