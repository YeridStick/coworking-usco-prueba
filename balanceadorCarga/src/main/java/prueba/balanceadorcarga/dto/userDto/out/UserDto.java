package prueba.balanceadorcarga.dto.userDto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String numIdent;
    private String nombre;
    private String correo;
    private Set<String> roles;
    private boolean enabled;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
