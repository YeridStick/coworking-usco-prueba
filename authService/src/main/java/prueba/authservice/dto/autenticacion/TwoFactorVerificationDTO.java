package prueba.authservice.dto.autenticacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoFactorVerificationDTO {
    private String email;
    private String code;
}
