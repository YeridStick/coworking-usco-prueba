package prueba.reservaservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recursos")
@Getter
@Setter
public class RecursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_recurso_id", nullable = false)
    @JsonBackReference
    private TipoRecursoEntity tipoRecurso;

    @Column(name = "codigo_identificacion", unique = true)
    private String codigoIdentificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoRecurso estado = EstadoRecurso.DISPONIBLE;

    @OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReservaEntity> reservas = new ArrayList<>();

    public enum EstadoRecurso {
        DISPONIBLE, EN_MANTENIMIENTO, FUERA_DE_SERVICIO, RESERVADO
    }
}