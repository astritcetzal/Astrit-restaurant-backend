package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.OrderDetail;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.dto.SalesReportDto;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.exception.OrderValidationException;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ClientRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.OrderRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientRepository clientRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, productRepository, clientRepository);
    }

    @Test
    void saveShouldBeAnnotatedWithTransactional() throws NoSuchMethodException {
        Method saveMethod = OrderService.class.getMethod("save", Order.class);
        assertTrue(saveMethod.isAnnotationPresent(Transactional.class));
    }

    @Test
    void saveShouldCalculateSubtotalsAndTotalOnServerIgnoringClientPrices() {
        Product tacos = buildProduct(1, "Tacos al pastor", 85.50, true);
        Product agua = buildProduct(3, "Agua de horchata", 35.00, true);

        when(productRepository.getProduct(1)).thenReturn(Optional.of(tacos));
        when(productRepository.getProduct(3)).thenReturn(Optional.of(agua));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order arg = invocation.getArgument(0);
            arg.setOrderId(10);
            return arg;
        });

        OrderDetail detail1 = new OrderDetail();
        detail1.setProductId(1);
        detail1.setQuantity(2);
        detail1.setUnitPrice(1.0);
        detail1.setSubtotal(1.0);

        OrderDetail detail2 = new OrderDetail();
        detail2.setProductId(3);
        detail2.setQuantity(3);
        detail2.setUnitPrice(0.0);
        detail2.setSubtotal(0.0);

        Order request = new Order();
        request.setClientId(2);
        request.setTableId(6);
        request.setPaymentMethod('E');
        request.setTotal(1.0);
        request.setDetails(List.of(detail1, detail2));

        Order result = orderService.save(request);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        Order persistedOrder = captor.getValue();

        assertEquals(276.00, persistedOrder.getTotal());
        assertEquals(10, result.getOrderId());
        assertEquals(276.00, result.getTotal());

        assertEquals("Tacos al pastor", result.getDetails().get(0).getProductName());
        assertEquals(85.50, result.getDetails().get(0).getUnitPrice());
        assertEquals(171.00, result.getDetails().get(0).getSubtotal());

        assertEquals("Agua de horchata", result.getDetails().get(1).getProductName());
        assertEquals(35.00, result.getDetails().get(1).getUnitPrice());
        assertEquals(105.00, result.getDetails().get(1).getSubtotal());
    }

    @Test
    void saveShouldThrowWhenProductIsNotAvailable() {
        Product unavailable = buildProduct(5, "Mole poblano", 140.00, false);
        when(productRepository.getProduct(5)).thenReturn(Optional.of(unavailable));

        OrderDetail detail = new OrderDetail();
        detail.setProductId(5);
        detail.setQuantity(1);

        Order request = new Order();
        request.setDetails(List.of(detail));

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> orderService.save(request));
        assertTrue(ex.getMessage().contains("Mole poblano"));
        assertTrue(ex.getMessage().contains("no está disponible"));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void saveShouldThrowWhenProductDoesNotExist() {
        when(productRepository.getProduct(99)).thenReturn(Optional.empty());

        OrderDetail detail = new OrderDetail();
        detail.setProductId(99);
        detail.setQuantity(1);

        Order request = new Order();
        request.setDetails(List.of(detail));

        OrderValidationException ex = assertThrows(OrderValidationException.class, () -> orderService.save(request));
        assertTrue(ex.getMessage().contains("99"));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void getSalesReportShouldAggregateRevenueOrdersAndClients() {
        when(orderRepository.getTotalRevenue()).thenReturn(new BigDecimal("1250.5"));
        when(orderRepository.countOrders()).thenReturn(15L);
        when(clientRepository.countClients()).thenReturn(8L);

        SalesReportDto report = orderService.getSalesReport();

        assertEquals(new BigDecimal("1250.50"), report.getTotalRevenue());
        assertEquals(15L, report.getTotalOrders());
        assertEquals(8L, report.getTotalClients());
    }

    private Product buildProduct(Integer id, String name, double price, Boolean available) {
        Product product = new Product();
        product.setProductId(id);
        product.setName(name);
        product.setPrice(price);
        product.setAvailable(available);
        return product;
    }
}
