package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.OrderDetail;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.DetallePedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel="spring", uses={ProductMapper.class})
public interface DetailsOrderMapper {
    @Mappings({
            @Mapping(source="id.idProducto", target="productId"),
            @Mapping(source="producto.nombre", target="productName"),
            @Mapping(source="cantidad", target="quantity"),
            @Mapping(source="producto.precio", target="unitPrice"),
            @Mapping(source="total", target="subtotal")
    })
    OrderDetail toOrderDetail(DetallePedido detallePedido);

    @Mappings({
            @Mapping(source="productId", target="id.idProducto"),
            @Mapping(source="quantity", target="cantidad"),
            @Mapping(source="subtotal", target="total"),
            @Mapping(target="pedido", ignore=true),
            @Mapping(target="producto", ignore=true),
            @Mapping(target = "id.idPedido", ignore=true)
    })
    DetallePedido toDetallePedido(OrderDetail item);
}
