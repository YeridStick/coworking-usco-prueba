package prueba.reservaservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reservas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UsuarioId", nullable = false)
    private String usuarioId; // Asociado al microservicio de usuarios

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RecursoId", nullable = false)
    @JsonIgnore
    private RecursoEntity recurso;

    @Column(name = "FechaInicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "FechaFin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "Estado", nullable = false)
    private String estado;
}
