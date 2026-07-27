package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Cliente;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Pedido;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteCrudRepository extends CrudRepository<Cliente, Integer> {

}
