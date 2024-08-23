package prueba.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RolesUser")
public class RolesEntity {
    @Id
    @Column(name = "IdRoll")
    private String idRoll;

    @Column(name = "Cargo")
    private String cargo;

    @Column(name = "Descripcion")
    private String descripcion;

    @PrePersist
    public void generateId() {
        String randomPart = Long.toHexString(Double.doubleToLongBits(Math.random()));
        String timeStampPart = Long.toHexString(System.currentTimeMillis());

        this.idRoll = (randomPart + timeStampPart).substring(0, 8);
    }
}