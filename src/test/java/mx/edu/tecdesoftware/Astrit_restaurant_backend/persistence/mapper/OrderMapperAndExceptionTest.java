package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.OrderDetail;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.exception.OrderValidationException;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.DetallePedido;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.DetallePedidoPK;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Pedido;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Producto;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.web.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperAndExceptionTest {

    @Test
    void orderMapperShouldReturnDishDetailsAndCalculatedTotal() {
        DetailsOrderMapper detailsOrderMapper = Mappers.getMapper(DetailsOrderMapper.class);
        OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
        ReflectionTestUtils.setField(orderMapper, "detailsOrderMapper", detailsOrderMapper);

        Producto producto1 = new Producto();
        producto1.setIdProducto(3);
        producto1.setNombre("Enchiladas verdes");
        producto1.setPrecio(95.0);

        DetallePedidoPK pk1 = new DetallePedidoPK();
        pk1.setIdPedido(12);
        pk1.setIdProducto(3);

        DetallePedido detalle1 = new DetallePedido();
        detalle1.setId(pk1);
        detalle1.setCantidad(2);
        detalle1.setTotal(190.0);
        detalle1.setProducto(producto1);

        Producto producto2 = new Producto();
        producto2.setIdProducto(7);
        producto2.setNombre("Limonada");
        producto2.setPrecio(30.0);

        DetallePedidoPK pk2 = new DetallePedidoPK();
        pk2.setIdPedido(12);
        pk2.setIdProducto(7);

        DetallePedido detalle2 = new DetallePedido();
        detalle2.setId(pk2);
        detalle2.setCantidad(1);
        detalle2.setTotal(30.0);
        detalle2.setProducto(producto2);

        Pedido pedido = new Pedido();
        pedido.setIdPedido(12);
        pedido.setIdCliente(2);
        pedido.setIdMesa(4);
        pedido.setMetodoPago('T');
        pedido.setDetalles(List.of(detalle1, detalle2));

        Order order = orderMapper.toOrder(pedido);

        assertEquals(12, order.getOrderId());
        assertEquals(2, order.getClientId());
        assertEquals(4, order.getTableId());
        assertEquals('T', order.getPaymentMethod());
        assertEquals(220.0, order.getTotal());
        assertEquals(2, order.getDetails().size());

        OrderDetail first = order.getDetails().get(0);
        assertEquals(3, first.getProductId());
        assertEquals("Enchiladas verdes", first.getProductName());
        assertEquals(2, first.getQuantity());
        assertEquals(95.0, first.getUnitPrice());
        assertEquals(190.0, first.getSubtotal());
    }

    @Test
    void globalExceptionHandlerShouldReturnBadRequestWithClearMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<Map<String, Object>> response = handler.handleOrderValidationException(
                new OrderValidationException("El platillo 'Mole' no está disponible.")
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("El platillo 'Mole' no está disponible.", response.getBody().get("message"));
    }
}
