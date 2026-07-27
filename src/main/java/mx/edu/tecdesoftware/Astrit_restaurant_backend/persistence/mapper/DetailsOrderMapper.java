package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.OrderDetail;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.DetallePedido;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Pedido;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring", uses={ProductMapper.class})
public interface DetailsOrderMapper {
    @Mappings({
            @Mapping(source="id.idProducto", target="productId"),
            @Mapping(source="cantidad", target="amount"),
            @Mapping(source="total", target="total")
    })
    OrderDetail toOrderDetail(DetallePedido detallePedido);
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target="pedido", ignore=true),
            @Mapping(target="producto", ignore=true),
            @Mapping(target = "id.idPedido", ignore=true)
    })
    DetallePedido toDetallePedido(OrderDetail item);
}
