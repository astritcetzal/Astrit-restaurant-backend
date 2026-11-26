package mx.edu.tecdesoftware.Astrit_restaurant_backend.security;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Role;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud.ClienteCrudRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Cliente;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDefaultAdmin(
            ClienteCrudRepository clienteCrudRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Cliente admin = clienteCrudRepository.findByNombre("admin")
                    .orElseGet(() -> {
                        Cliente nuevo = new Cliente();
                        nuevo.setNombre("admin");
                        nuevo.setContrasena(passwordEncoder.encode("admin123"));
                        return nuevo;
                    });

            if (!Role.ADMIN.name().equals(admin.getRol())) {
                admin.setRol(Role.ADMIN.name());
                clienteCrudRepository.save(admin);
            }
        };
    }
}
