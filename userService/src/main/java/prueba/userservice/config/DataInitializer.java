package prueba.userservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import prueba.userservice.entity.RolesEntity;
import prueba.userservice.entity.UserEntity;
import prueba.userservice.repository.RolesRepository;
import prueba.userservice.repository.UserRepository;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(RolesRepository rolesRepository,
                                   UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            logger.info("Initializing database...");

            // Create admin role if it doesn't exist
            RolesEntity adminRole = rolesRepository.findByCargo("ADMIN")
                    .orElseGet(() -> {
                        logger.info("Creating ADMIN role...");
                        RolesEntity newRole = new RolesEntity();
                        newRole.setCargo("ADMIN");
                        newRole.setDescripcion("Rol de administrador con todos los permisos");
                        return rolesRepository.save(newRole);
                    });
            logger.info("ADMIN role: {}", adminRole);

            // Create user role if it doesn't exist
            RolesEntity userRole = rolesRepository.findByCargo("USER")
                    .orElseGet(() -> {
                        logger.info("Creating USER role...");
                        RolesEntity newRole = new RolesEntity();
                        newRole.setCargo("USER");
                        newRole.setDescripcion("Rol de usuario estándar");
                        return rolesRepository.save(newRole);
                    });
            logger.info("USER role: {}", userRole);

            // Create admin user if it doesn't exist
            String adminPassword = "adminPassword";  // You can change this to any password you prefer
            if (userRepository.findByCorreo("admin@coworking.com").isEmpty()) {
                logger.info("Creating admin user...");
                UserEntity adminUser = new UserEntity();
                adminUser.setNumIdent("00000001");
                adminUser.setNombre("Administrador");
                adminUser.setCorreo("admin@coworking.com");
                adminUser.setPassword(passwordEncoder.encode(adminPassword));
                adminUser.setTipoRoll(adminRole);
                userRepository.save(adminUser);
                logger.info("Admin user created with email: admin@coworking.com and password: {}", adminPassword);
            }

            logger.info("Database initialization completed.");
        };
    }
}