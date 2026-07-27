package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> getAll();
    Optional<List<Order>> getByClientId(Integer clienteId);
    void delete(int pedidoId);
    Order save(Order order);

}
