package prueba.balanceadorcarga.dto.auth.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
    private String correo;
    private String password;
    private String roll;
    private String twoFactorCode;
}