package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteCrudRepository extends CrudRepository<Cliente, String> {
}
