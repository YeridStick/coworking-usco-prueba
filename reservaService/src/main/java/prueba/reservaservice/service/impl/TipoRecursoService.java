package prueba.reservaservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.reservaservice.dto.recursos.RecursoRequestDto;
import prueba.reservaservice.dto.tipoRecursos.TipoRecursoRequestDto;
import prueba.reservaservice.dto.tipoRecursos.TipoRecursoResponseDto;
import prueba.reservaservice.entity.RecursoEntity;
import prueba.reservaservice.entity.TipoRecursoEntity;
import prueba.reservaservice.repository.TipoRecursoRepository;
import prueba.reservaservice.service.ITipoRecursoService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoRecursoService implements ITipoRecursoService {

    private final TipoRecursoRepository tipoRecursoRepository;
    private final ModelMapper modelMapper;
    private final RecursoService recursoService;

    @Autowired
    public TipoRecursoService(TipoRecursoRepository tipoRecursoRepository, ModelMapper modelMapper, RecursoService recursoService) {
        this.tipoRecursoRepository = tipoRecursoRepository;
        this.modelMapper = modelMapper;
        this.recursoService = recursoService;
    }

    @Override
    @Transactional
    public TipoRecursoResponseDto createTipoRecurso(TipoRecursoRequestDto tipoRecursoDto) {
        TipoRecursoEntity.CategoriaRecurso categoria;
        try {
            categoria = TipoRecursoEntity.CategoriaRecurso.valueOf(tipoRecursoDto.getCategoria().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoría no válida. Las categorías válidas son: "
                    + Arrays.toString(TipoRecursoEntity.CategoriaRecurso.values()));
        }

        validateTipoRecursoDto(tipoRecursoDto);

        TipoRecursoEntity tipoRecurso = new TipoRecursoEntity();
        tipoRecurso.setNombre(tipoRecursoDto.getNombre());
        tipoRecurso.setDescripcion(tipoRecursoDto.getDescripcion());
        tipoRecurso.setCategoria(categoria);
        tipoRecurso.setCantidadTotal(tipoRecursoDto.getCantidadTotal());
        tipoRecurso.setCantidadDisponible(0); // la cantidad disponible de penderan de los recursos que se encuentren en este estado

        TipoRecursoEntity savedTipoRecurso = tipoRecursoRepository.save(tipoRecurso);
        List<RecursoEntity> recursos = new ArrayList<>();

        try {
            for (int i = 1; i <= savedTipoRecurso.getCantidadTotal(); i++) {
                RecursoRequestDto recursoDto = new RecursoRequestDto();
                recursoDto.setTipoRecursoId(savedTipoRecurso.getId());
                recursoDto.setCodigoIdentificacion(generateCodigoIdentificacion(savedTipoRecurso.getNombre(), i));
                recursoDto.setEstado(RecursoEntity.EstadoRecurso.DISPONIBLE.name());

                RecursoEntity recurso = recursoService.createRecurso(recursoDto);
                recursos.add(recurso);
            }

            savedTipoRecurso.setRecursos(recursos);
            savedTipoRecurso = tipoRecursoRepository.save(savedTipoRecurso);
        } catch (Exception e) {
            tipoRecursoRepository.delete(savedTipoRecurso);
            recursos.forEach(recurso -> recursoService.deleteRecurso(recurso.getId()));
            throw new RuntimeException("Error al crear el tipo de recurso y sus instancias: " + e.getMessage(), e);
        }

        return modelMapper.map(savedTipoRecurso, TipoRecursoResponseDto.class);
    }

    private void validateTipoRecursoDto(TipoRecursoRequestDto dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tipo de recurso no puede estar vacío");
        }
        if (dto.getCantidadTotal() == null || dto.getCantidadTotal() <= 0) {
            throw new IllegalArgumentException("La cantidad total debe ser un número positivo");
        }
    }

    private String generateCodigoIdentificacion(String nombre, int index) {
        return String.format("%s-%03d", nombre.toUpperCase().replace(" ", "-"), index);
    }

    @Override
    @Transactional
    public TipoRecursoResponseDto updateTipoRecurso(Long id, TipoRecursoRequestDto tipoRecursoDto) {
        TipoRecursoEntity tipoRecurso = tipoRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de recurso no encontrado"));
        modelMapper.map(tipoRecursoDto, tipoRecurso);
        TipoRecursoEntity updatedTipoRecurso = tipoRecursoRepository.save(tipoRecurso);
        return modelMapper.map(updatedTipoRecurso, TipoRecursoResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoRecursoResponseDto getTipoRecursoById(Long id) {
        TipoRecursoEntity tipoRecurso = tipoRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de recurso no encontrado"));
        return modelMapper.map(tipoRecurso, TipoRecursoResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoRecursoResponseDto> getAllTiposRecurso() {
        return tipoRecursoRepository.findAll().stream()
                .map(tipoRecurso -> modelMapper.map(tipoRecurso, TipoRecursoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTipoRecurso(Long id) {
        if (!tipoRecursoRepository.existsById(id)) {
            throw new RuntimeException("Tipo de recurso no encontrado");
        }
        tipoRecursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TipoRecursoResponseDto updateTipoRecursoCategoria(Long id, TipoRecursoEntity.CategoriaRecurso nuevaCategoria) {
        TipoRecursoEntity tipoRecurso = tipoRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de recurso no encontrado"));

        tipoRecurso.setCategoria(nuevaCategoria);
        TipoRecursoEntity updatedTipoRecurso = tipoRecursoRepository.save(tipoRecurso);

        return modelMapper.map(updatedTipoRecurso, TipoRecursoResponseDto.class);
    }
}