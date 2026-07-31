package mx.edu.tecdesoftware.Astrit_restaurant_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(title = "Astrit Restaurant API", version = "1.0"),
		security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)

@SpringBootApplication
public class AstritRestaurantBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(AstritRestaurantBackendApplication.class, args);
	}

}
