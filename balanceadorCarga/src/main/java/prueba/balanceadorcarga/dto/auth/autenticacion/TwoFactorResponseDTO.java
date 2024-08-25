package prueba.balanceadorcarga.dto.auth.autenticacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwoFactorResponseDTO {
    private boolean twoFactorRequired;
    private String message;
}