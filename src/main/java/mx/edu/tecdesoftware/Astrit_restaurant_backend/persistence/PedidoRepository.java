package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Table;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.OrderRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud.PedidoCrudRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Pedido;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class PedidoRepository  implements OrderRepository {
    @Autowired
    private PedidoCrudRepository pedidoCrudRepository;
    @Autowired
    private OrderMapper orderMapper;

    public List<Order> getAll(){
        List<Pedido> pedidos = (List<Pedido>) pedidoCrudRepository.findAll();
        return orderMapper.toOrders(pedidos);
    }
    public Optional<Order> getOrder(Integer orderId){
        return pedidoCrudRepository.findById(orderId)
                .map(pedido -> orderMapper.toOrder(pedido));
    }
    public Optional<List<Order>> getByClientId(Integer clienteId){
        return pedidoCrudRepository.findByIdCliente(clienteId)
                .map(pedidos -> orderMapper.toOrders(pedidos));
    }
    public Order save(Order order){
        Pedido pedido = orderMapper.toPedido((order));
        if (pedido.getPedido()!= null){
            pedido.getPedido().forEach(detalle -> detalle.setPedido(pedido));
        }
        return orderMapper.toOrder(pedidoCrudRepository.save(pedido));
    }
    public void delete(int idPedido){
        pedidoCrudRepository.deleteById(idPedido);
    }

}
