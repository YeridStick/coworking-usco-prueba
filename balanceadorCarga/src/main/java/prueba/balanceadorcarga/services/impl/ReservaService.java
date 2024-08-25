package prueba.balanceadorcarga.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prueba.balanceadorcarga.client.ReservaClient;
import prueba.balanceadorcarga.dto.reservaDTO.RecursoEntity;
import prueba.balanceadorcarga.dto.reservaDTO.ReservaRequestDto;
import prueba.balanceadorcarga.dto.reservaDTO.ReservaResponseDto;
import prueba.balanceadorcarga.dto.reservaDTO.TipoRecursoEntity;
import prueba.balanceadorcarga.dto.reservaDTO.horarioDto.RecursoDisponibilidadDto;
import prueba.balanceadorcarga.dto.reservaDTO.recursos.RecursoResponseDto;
import prueba.balanceadorcarga.dto.reservaDTO.tipoRecursos.TipoRecursoRequestDto;
import prueba.balanceadorcarga.dto.reservaDTO.tipoRecursos.TipoRecursoResponseDto;
import prueba.balanceadorcarga.services.IReservaService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    private final ReservaClient reservaClient;

    @Autowired
    public ReservaService(ReservaClient reservaClient) {
        this.reservaClient = reservaClient;
    }

    @Override
    public ReservaResponseDto createReserva(ReservaRequestDto reservaDto) {
        return reservaClient.createReserva(reservaDto).getBody();
    }

    @Override
    public ReservaResponseDto updateReserva(Long id, ReservaRequestDto updatedReservaDto) {
        return reservaClient.updateReserva(id, updatedReservaDto).getBody();
    }

    @Override
    public List<ReservaResponseDto> getAllReservas() {
        return reservaClient.getAllReservas().getBody();
    }

    @Override
    public ReservaResponseDto getReservaById(Long id) {
        return reservaClient.getReservaById(id).getBody();
    }

    @Override
    public ReservaResponseDto deleteReserva(Long id) {
        return reservaClient.deleteReserva(id).getBody();
    }

    // Recursos
    @Override
    public List<RecursoResponseDto> getAllRecursos() {
        return reservaClient.getAllRecursos().getBody();
    }

    @Override
    public RecursoResponseDto getRecursoById(Long id) {
        return reservaClient.getRecursoById(id).getBody();
    }

    @Override
    public void deleteRecurso(Long id) {
        reservaClient.deleteRecurso(id);
    }

    @Override
    public RecursoDisponibilidadDto getRecursoDisponibilidad(Long recursoId, LocalDateTime desde, LocalDateTime hasta) {
        return reservaClient.getRecursoDisponibilidad(recursoId, desde, hasta).getBody();
    }

    @Override
    public void updateRecursoEstado(Long id, RecursoEntity.EstadoRecurso nuevoEstado) {
        reservaClient.updateRecursoEstado(id, nuevoEstado);
    }

    @Override
    public List<RecursoEntity.EstadoRecurso> getAllEstadosRecurso() {
        return reservaClient.getAllEstadosRecurso().getBody();
    }

    @Override
    public List<RecursoResponseDto> getRecursosPorTipo(Long tipoRecursoId) {
        return reservaClient.getRecursosPorTipo(tipoRecursoId).getBody();
    }

    @Override
    public String getEstadoRecurso(Long recursoId) {
        return reservaClient.getEstadoRecurso(recursoId).getBody();
    }

    // Tipo de Reserva
    @Override
    public TipoRecursoResponseDto createTipoRecurso(TipoRecursoRequestDto tipoRecursoDto) {
        return reservaClient.createTipoRecurso(tipoRecursoDto).getBody();
    }

    @Override
    public TipoRecursoResponseDto updateTipoRecurso(Long id, TipoRecursoRequestDto tipoRecursoDto) {
        return reservaClient.updateTipoRecurso(id, tipoRecursoDto).getBody();
    }

    @Override
    public TipoRecursoResponseDto getTipoRecursoById(Long id) {
        return reservaClient.getTipoRecursoById(id).getBody();
    }

    @Override
    public List<TipoRecursoResponseDto> getAllTiposRecurso() {
        return reservaClient.getAllTiposRecurso().getBody();
    }

    @Override
    public void deleteTipoRecurso(Long id) {
        reservaClient.deleteTipoRecurso(id);
    }

    @Override
    public List<TipoRecursoEntity.CategoriaRecurso> getAllCategorias() {
        return reservaClient.getAllCategorias().getBody();
    }

    @Override
    public TipoRecursoResponseDto updateTipoRecursoCategoria(Long id, TipoRecursoEntity.CategoriaRecurso nuevaCategoria) {
        return reservaClient.updateTipoRecursoCategoria(id, nuevaCategoria).getBody();
    }

    // Categoría
    @Override
    public List<String> getCategorias() {
        return reservaClient.getCategorias().getBody();
    }
}