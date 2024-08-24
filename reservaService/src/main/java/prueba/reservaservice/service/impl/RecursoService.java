package prueba.reservaservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.reservaservice.client.UserServiceClient;
import prueba.reservaservice.dto.horarioDto.RecursoDisponibilidadDto;
import prueba.reservaservice.dto.recursos.RecursoRequestDto;
import prueba.reservaservice.dto.recursos.RecursoResponseDto;
import prueba.reservaservice.entity.RecursoEntity;
import prueba.reservaservice.entity.ReservaEntity;
import prueba.reservaservice.entity.TipoRecursoEntity;
import prueba.reservaservice.repository.RecursoRepository;
import prueba.reservaservice.repository.ReservaRepository;
import prueba.reservaservice.repository.TipoRecursoRepository;
import prueba.reservaservice.service.IRecursoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecursoService implements IRecursoService {

    private final RecursoRepository recursoRepository;
    private final TipoRecursoRepository tipoRecursoRepository;
    private final ModelMapper modelMapper;
    private final ReservaRepository reservaRepository;
    private final UserServiceClient userServiceClient;

    @Autowired
    public RecursoService(RecursoRepository recursoRepository, TipoRecursoRepository tipoRecursoRepository, ModelMapper modelMapper, ReservaRepository reservaRepository, UserServiceClient userServiceClient) {
        this.recursoRepository = recursoRepository;
        this.tipoRecursoRepository = tipoRecursoRepository;
        this.modelMapper = modelMapper;
        this.reservaRepository = reservaRepository;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    @Override
    public RecursoEntity createRecurso(RecursoRequestDto recursoDto) {
        TipoRecursoEntity tipoRecurso = tipoRecursoRepository.findById(recursoDto.getTipoRecursoId())
                .orElseThrow(() -> new RuntimeException("Tipo de recurso no encontrado"));

        RecursoEntity recurso = new RecursoEntity();
        recurso.setTipoRecurso(tipoRecurso);
        recurso.setCodigoIdentificacion(recursoDto.getCodigoIdentificacion());
        recurso.setEstado(RecursoEntity.EstadoRecurso.DISPONIBLE);

        RecursoEntity savedRecurso = recursoRepository.save(recurso);

        tipoRecurso.setCantidadDisponible(tipoRecurso.getCantidadDisponible() + 1);
        tipoRecursoRepository.save(tipoRecurso);

        return savedRecurso;
    }

    @Transactional
    @Override
    public RecursoResponseDto updateRecurso(Long id, RecursoRequestDto recursoDto) {
        RecursoEntity recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        modelMapper.map(recursoDto, recurso);
        recurso.setTipoRecurso(tipoRecursoRepository.findById(recursoDto.getTipoRecursoId())
                .orElseThrow(() -> new RuntimeException("Tipo de recurso no encontrado")));
        RecursoEntity updatedRecurso = recursoRepository.save(recurso);
        return modelMapper.map(updatedRecurso, RecursoResponseDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RecursoResponseDto> getAllRecursos() {
        return recursoRepository.findAll().stream()
                .map(recurso -> modelMapper.map(recurso, RecursoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public RecursoResponseDto getRecursoById(Long id) {
        RecursoEntity recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return modelMapper.map(recurso, RecursoResponseDto.class);
    }

    @Transactional
    @Override
    public void deleteRecurso(Long id) {
        if (!recursoRepository.existsById(id)) {
            throw new RuntimeException("Recurso no encontrado");
        }
        recursoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public RecursoDisponibilidadDto getRecursoDisponibilidad(Long recursoId, LocalDateTime desde, LocalDateTime hasta) {
        RecursoEntity recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        List<ReservaEntity> reservas = reservaRepository.findByRecursoIdAndFechaInicioBetween(recursoId, desde, hasta);

        RecursoDisponibilidadDto disponibilidadDto = new RecursoDisponibilidadDto();
        disponibilidadDto.setRecursoId(recurso.getId());
        disponibilidadDto.setCodigoIdentificacion(recurso.getCodigoIdentificacion());
        disponibilidadDto.setTipoRecursoId(recurso.getTipoRecurso().getId());
        disponibilidadDto.setNombreTipoRecurso(recurso.getTipoRecurso().getNombre());
        disponibilidadDto.setEstado(recurso.getEstado().toString());

        List<RecursoDisponibilidadDto.ReservaInfoDto> reservaInfoDtos = reservas.stream()
                .map(this::mapReservaToReservaInfoDto)
                .collect(Collectors.toList());

        disponibilidadDto.setReservas(reservaInfoDtos);

        return disponibilidadDto;
    }

    private RecursoDisponibilidadDto.ReservaInfoDto mapReservaToReservaInfoDto(ReservaEntity reserva) {
        RecursoDisponibilidadDto.ReservaInfoDto infoDto = new RecursoDisponibilidadDto.ReservaInfoDto();
        infoDto.setReservaId(reserva.getId());
        infoDto.setUsuarioId(reserva.getUsuarioId());
        infoDto.setFechaInicio(reserva.getFechaInicio());
        infoDto.setFechaFin(reserva.getFechaFin());

        // Obtener el correo del usuario usando el cliente Feign
        String correoUsuario = userServiceClient.getUserById(reserva.getUsuarioId());
        infoDto.setCorreoUsuario(correoUsuario);

        return infoDto;
    }

    @Transactional
    public RecursoResponseDto updateRecursoEstado(Long recursoId, RecursoEntity.EstadoRecurso nuevoEstado) {
        RecursoEntity recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        RecursoEntity.EstadoRecurso estadoAnterior = recurso.getEstado();
        recurso.setEstado(nuevoEstado);

        TipoRecursoEntity tipoRecurso = recurso.getTipoRecurso();

        // Actualizar cantidadDisponible en TipoRecursoEntity
        if (estadoAnterior == RecursoEntity.EstadoRecurso.DISPONIBLE && nuevoEstado != RecursoEntity.EstadoRecurso.DISPONIBLE) {
            tipoRecurso.setCantidadDisponible(tipoRecurso.getCantidadDisponible() - 1);
        } else if (estadoAnterior != RecursoEntity.EstadoRecurso.DISPONIBLE && nuevoEstado == RecursoEntity.EstadoRecurso.DISPONIBLE) {
            tipoRecurso.setCantidadDisponible(tipoRecurso.getCantidadDisponible() + 1);
        }

        tipoRecursoRepository.save(tipoRecurso);
        RecursoEntity savedRecurso = recursoRepository.save(recurso);

        // Convertir la entidad a DTO
        RecursoResponseDto responseDto = new RecursoResponseDto();
        responseDto.setId(savedRecurso.getId());
        responseDto.setTipoRecursoId(tipoRecurso.getId());
        responseDto.setTipoRecursoNombre(tipoRecurso.getNombre());
        responseDto.setCodigoIdentificacion(savedRecurso.getCodigoIdentificacion());
        responseDto.setEstado(savedRecurso.getEstado().name());

        return responseDto;
    }

    @Override
    public List<RecursoResponseDto> getRecursosPorTipo(Long tipoRecursoId) {
        List<RecursoEntity> recursos = recursoRepository.findByTipoRecursoId(tipoRecursoId);
        return recursos.stream()
                .map(recurso -> modelMapper.map(recurso, RecursoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getEstadoRecurso(Long recursoId) {
        RecursoEntity recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return recurso.getEstado().toString();
    }

}