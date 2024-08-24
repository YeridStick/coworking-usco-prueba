package prueba.reservaservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.reservaservice.client.UserServiceClient;
import prueba.reservaservice.dto.ReservaRequestDto;
import prueba.reservaservice.dto.ReservaResponseDto;
import prueba.reservaservice.dto.user.UserDto;
import prueba.reservaservice.entity.RecursoEntity;
import prueba.reservaservice.entity.ReservaEntity;
import prueba.reservaservice.repository.RecursoRepository;
import prueba.reservaservice.repository.ReservaRepository;
import prueba.reservaservice.service.IReservaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IReservaService {

    private final RecursoRepository recursoRepository;
    private final ReservaRepository reservaRepository;
    private final NotificationService notificationService;
    private final UserServiceClient userServiceClient;
    private final ModelMapper modelMapper;

    @Autowired
    public ReservaService(RecursoRepository recursoRepository, ReservaRepository reservaRepository, NotificationService notificationService,
                          UserServiceClient userServiceClient, ModelMapper modelMapper) {
        this.recursoRepository = recursoRepository;
        this.reservaRepository = reservaRepository;
        this.notificationService = notificationService;
        this.userServiceClient = userServiceClient;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public ReservaResponseDto createReserva(ReservaRequestDto reservaDto) {
        if (userServiceClient.getUserById(reservaDto.getUsuarioId()).isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        RecursoEntity recurso = recursoRepository.findById(reservaDto.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        ReservaEntity reserva = new ReservaEntity();

        reserva.setRecurso(recurso);
        reserva.setUsuarioId(reservaDto.getUsuarioId());
        reserva.setFechaInicio(reservaDto.getFechaInicio());
        reserva.setFechaFin(reservaDto.getFechaFin());

        ReservaEntity savedReserva = reservaRepository.save(reserva);
        notificationService.sendReservationUpdate(savedReserva);
        return modelMapper.map(savedReserva, ReservaResponseDto.class);
    }

    @Transactional
    @Override
    public ReservaResponseDto updateReserva(Long id, ReservaRequestDto updatedReservaDto) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        modelMapper.map(updatedReservaDto, reserva);
        ReservaEntity savedReserva = reservaRepository.save(reserva);
        notificationService.sendReservationUpdate(savedReserva);
        return modelMapper.map(savedReserva, ReservaResponseDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReservaResponseDto> getAllReservas() {
        return reservaRepository.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ReservaResponseDto getReservaById(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return modelMapper.map(reserva, ReservaResponseDto.class);
    }

    @Transactional
    @Override
    public void deleteReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada");
        }
        reservaRepository.deleteById(id);
    }
}