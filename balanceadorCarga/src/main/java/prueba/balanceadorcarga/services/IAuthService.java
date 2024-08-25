package prueba.balanceadorcarga.services;

import prueba.balanceadorcarga.dto.auth.LoginResponseDTO;
import prueba.balanceadorcarga.dto.auth.autenticacion.TwoFactorResponseDTO;
import prueba.balanceadorcarga.dto.auth.userDto.UserGinDTO;

public interface IAuthService {

    TwoFactorResponseDTO login(UserGinDTO userGinDTO);

    LoginResponseDTO verifyTwoFactor(String email, String code);
}