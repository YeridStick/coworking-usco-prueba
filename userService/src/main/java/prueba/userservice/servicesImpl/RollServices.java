package prueba.userservice.servicesImpl;

import org.springframework.stereotype.Service;
import prueba.userservice.entity.RolesEntity;
import prueba.userservice.repository.RolesRepository;
import prueba.userservice.services.IRollServices;

import java.util.Optional;

@Service
public class RollServices implements IRollServices {
    private final RolesRepository rolesRepository;

    public RollServices(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }
    @Override
    public String getCargo(String idRole) {
        Optional<RolesEntity> rol = rolesRepository.findByIdRoll(idRole);
        return rol.get().getCargo();
    }
}
