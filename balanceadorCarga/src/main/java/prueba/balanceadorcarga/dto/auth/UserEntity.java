package prueba.balanceadorcarga.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String numIdent;
    private String nombre;
    private String correo;
    private Long telefono;
    private String password;
}
