package prueba.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Usuario")
public class UserEntity {
    @Id
    @Column(name = "NumeroIdentificacion")
    private String numIdent;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Correo", unique = true, nullable = false)
    private String correo;

    @Column(name = "Password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoRoll", nullable = false)
    private RolesEntity tipoRoll;

    @CreationTimestamp
    @Column(name = "FechaCreacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "FechaActualizacion")
    private LocalDateTime fechaActualizacion;
}