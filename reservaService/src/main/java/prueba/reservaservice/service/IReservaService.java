package prueba.reservaservice.service;

import prueba.reservaservice.dto.ReservaRequestDto;
import prueba.reservaservice.dto.ReservaResponseDto;
import prueba.reservaservice.entity.ReservaEntity;

import java.util.List;

public interface IReservaService {
    ReservaResponseDto createReserva(ReservaRequestDto reservaDto);
    ReservaResponseDto updateReserva(Long id, ReservaRequestDto updatedReservaDto);
    List<ReservaResponseDto> getAllReservas();
    ReservaResponseDto getReservaById(Long id);
    void deleteReserva(Long id);
}
