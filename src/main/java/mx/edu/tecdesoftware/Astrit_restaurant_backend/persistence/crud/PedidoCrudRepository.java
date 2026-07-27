package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Pedido;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PedidoCrudRepository extends CrudRepository<Pedido, Integer> {
    Optional<Pedido> findByIdClientId(Integer clienteId);
}