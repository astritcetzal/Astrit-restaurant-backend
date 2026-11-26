package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.DetallePedido;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Pedido;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Mapper(componentModel = "spring", uses={DetailsOrderMapper.class})
public interface OrderMapper {
    @Mappings({
            @Mapping(source="idPedido", target="orderId"),
            @Mapping(source="idCliente", target="clientId"), //resolver nombre
            @Mapping(source="idMesa", target="tableId"),
            @Mapping(source="metodoPago", target="paymentMethod"),
            @Mapping(target="total", expression="java(calculateTotal(pedido))"),
            @Mapping(source="detalles", target="details")
    })
    Order toOrder(Pedido pedido);

    List<Order> toOrders(List<Pedido> pedidos);
    @InheritInverseConfiguration
    @Mapping(target="mesa", ignore = true)
    @Mapping(target="cliente", ignore=true)
    Pedido toPedido(Order order);

    default double calculateTotal(Pedido pedido) {
        if (pedido == null || pedido.getDetalles() == null) {
            return 0.0;
        }
        BigDecimal total = pedido.getDetalles().stream()
                .map(DetallePedido::getTotal)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
