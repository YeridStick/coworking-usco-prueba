package prueba.reservaservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.reservaservice.client.UserServiceClient;
import prueba.reservaservice.dto.ReservaRequestDto;
import prueba.reservaservice.dto.ReservaResponseDto;
import prueba.reservaservice.dto.UserAuthDto;
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
    private final RecursoService recursoService;
    private final ReservaRepository reservaRepository;
    private final NotificationService notificationService;
    private final ModelMapper modelMapper;
    private final UserServiceClient userServiceClient;

    @Autowired
    public ReservaService(RecursoRepository recursoRepository, RecursoService recursoService, ReservaRepository reservaRepository,
                          NotificationService notificationService, ModelMapper modelMapper, UserServiceClient userServiceClient) {
        this.recursoRepository = recursoRepository;
        this.recursoService = recursoService;
        this.reservaRepository = reservaRepository;
        this.notificationService = notificationService;
        this.modelMapper = modelMapper;
        this.userServiceClient = userServiceClient;
    }

    @Override
    @Transactional
    public ReservaResponseDto createReserva(ReservaRequestDto reservaDto) {
        RecursoEntity recurso = recursoRepository.findById(reservaDto.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        if (recurso.getEstado() != RecursoEntity.EstadoRecurso.DISPONIBLE) {
            throw new RuntimeException("El recurso no está disponible para reserva");
        }

        ReservaEntity reserva = new ReservaEntity();
        reserva.setUsuarioId(reservaDto.getUsuarioId());
        reserva.setRecurso(recurso);
        reserva.setFechaInicio(reservaDto.getFechaInicio());
        reserva.setFechaFin(reservaDto.getFechaFin());
        reserva.setEstado("CONFIRMADA");

        ReservaEntity savedReserva = reservaRepository.save(reserva);

        recursoService.updateRecursoEstado(recurso.getId(), RecursoEntity.EstadoRecurso.RESERVADO);

        return modelMapper.map(savedReserva, ReservaResponseDto.class);
    }

    @Transactional
    @Override
    public ReservaResponseDto updateReserva(Long id, ReservaRequestDto updatedReservaDto) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!reserva.getRecurso().getId().equals(updatedReservaDto.getRecursoId())) {
            recursoService.updateRecursoEstado(reserva.getRecurso().getId(), RecursoEntity.EstadoRecurso.DISPONIBLE);

            RecursoEntity nuevoRecurso = recursoRepository.findById(updatedReservaDto.getRecursoId())
                    .orElseThrow(() -> new RuntimeException("Nuevo recurso no encontrado"));

            if (nuevoRecurso.getEstado() != RecursoEntity.EstadoRecurso.DISPONIBLE) {
                throw new RuntimeException("El nuevo recurso no está disponible para reserva");
            }

            recursoService.updateRecursoEstado(nuevoRecurso.getId(), RecursoEntity.EstadoRecurso.RESERVADO);

            reserva.setRecurso(nuevoRecurso);
        }

        reserva.setFechaInicio(updatedReservaDto.getFechaInicio());
        reserva.setFechaFin(updatedReservaDto.getFechaFin());
        reserva.setUsuarioId(updatedReservaDto.getUsuarioId());

        ReservaEntity savedReserva = reservaRepository.save(reserva);
        notificationService.sendReservationUpdate(savedReserva);
        return modelMapper.map(savedReserva, ReservaResponseDto.class);
    }


    @Transactional(readOnly = true)
    @Override
    public List<ReservaResponseDto> getAllReservas() {
        return reservaRepository.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaResponseDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ReservaResponseDto getReservaById(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return modelMapper.map(reserva, ReservaResponseDto.class);
    }

    @Override
    @Transactional
    public ReservaResponseDto cancelReserva(Long reservaId) {
        ReservaEntity reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!"CONFIRMADA".equals(reserva.getEstado())) {
            throw new RuntimeException("Solo se pueden cancelar reservas confirmadas");
        }

        reserva.setEstado("CANCELADA");
        reservaRepository.delete(reserva);

        // Actualizar el estado del recurso a DISPONIBLE
        recursoService.updateRecursoEstado(reserva.getRecurso().getId(), RecursoEntity.EstadoRecurso.DISPONIBLE);

        return modelMapper.map(reserva, ReservaResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponseDto> getReservasByEmail(String email) {
        ResponseEntity<UserAuthDto> userResponse = userServiceClient.getByEmail(email);

        if (userResponse.getStatusCode() != HttpStatus.OK || userResponse.getBody() == null) {
            throw new RuntimeException("Usuario no encontrado o error en el servicio de usuarios");
        }

        UserAuthDto user = userResponse.getBody();

        List<ReservaEntity> reservas = reservaRepository.findByUsuarioId(user.getNumIdent());
        return reservas.stream()
                .map(reserva -> modelMapper.map(reserva, ReservaResponseDto.class))
                .collect(Collectors.toList());
    }
}