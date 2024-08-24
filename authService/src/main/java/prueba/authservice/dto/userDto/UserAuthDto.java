package prueba.authservice.dto.userDto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
    private String correo;
    private String password;
    private String roll;
}