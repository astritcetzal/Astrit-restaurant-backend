package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Pedido;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses={DetailsOrderMapper.class})
public interface OrderMapper {
    @Mappings({
            @Mapping(source="idPedido", target="orderId"),
            @Mapping(source="idCliente", target="clientId"), //resolver nombre
            @Mapping(source="idMesa", target="tableId"),
            @Mapping(source="metodoPago", target="paymentMethod"),
            @Mapping(source="pedido", target="orders")
    })
    Order toOrder(Pedido pedido);

    List<Order> toOrders(List<Pedido> pedidos);
    @InheritInverseConfiguration
    @Mapping(target="mesa", ignore = true)
    @Mapping(target="cliente", ignore=true)
    Pedido toPedido(Order order);

}
