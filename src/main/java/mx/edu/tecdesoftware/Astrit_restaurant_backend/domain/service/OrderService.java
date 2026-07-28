package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public List<Order> getAll(){
        return orderRepository.getAll();
    }
    public Optional<Order> getOrder(Integer orderId){
        return orderRepository.getOrder(orderId);
    }
    public Optional<List<Order>> getByClient(Integer clientId){
        return orderRepository.getByClientId(clientId);
    }
    public Order save(Order order){
        return orderRepository.save(order);
    }
    public boolean delete(int orderId){
        if(getOrder(orderId).isPresent()){
           orderRepository.delete(orderId);
            return true;
        }{
            return false;
        }
    }
}
