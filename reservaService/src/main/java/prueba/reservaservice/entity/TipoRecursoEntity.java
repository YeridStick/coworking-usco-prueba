package prueba.reservaservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipos_recursos")
@Getter
@Setter
public class TipoRecursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaRecurso categoria;

    @Column(name = "cantidad_total")
    private Integer cantidadTotal;

    @Column(name = "cantidad_disponible")
    private Integer cantidadDisponible;

    @OneToMany(mappedBy = "tipoRecurso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecursoEntity> recursos = new ArrayList<>();

    public enum CategoriaRecurso {
        SALA, EQUIPAMIENTO
    }
}