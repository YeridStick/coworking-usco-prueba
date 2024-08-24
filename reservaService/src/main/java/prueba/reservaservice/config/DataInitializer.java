package prueba.reservaservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prueba.reservaservice.entity.TipoRecursoEntity;
import prueba.reservaservice.entity.RecursoEntity;
import prueba.reservaservice.repository.TipoRecursoRepository;
import prueba.reservaservice.repository.RecursoRepository;

@Configuration
public class DataInitializer {

    @Autowired
    private TipoRecursoRepository tipoRecursoRepository;

    /*@Bean
    public CommandLineRunner initData() {
        return args -> {
            if (tipoRecursoRepository.count() == 0) {
                TipoRecursoEntity sala = new TipoRecursoEntity();
                sala.setNombre("Sala de Reuniones");
                sala.setDescripcion("Espacio para reuniones");
                sala.setCategoria(TipoRecursoEntity.CategoriaRecurso.SALA);
                tipoRecursoRepository.save(sala);

                TipoRecursoEntity equipo = new TipoRecursoEntity();
                equipo.setNombre("Proyector");
                equipo.setDescripcion("Equipo para presentaciones");
                equipo.setCategoria(TipoRecursoEntity.CategoriaRecurso.EQUIPAMIENTO);
                tipoRecursoRepository.save(equipo);

                System.out.println("Tipos de recursos iniciales creados.");
            }
        };
    }*/
}