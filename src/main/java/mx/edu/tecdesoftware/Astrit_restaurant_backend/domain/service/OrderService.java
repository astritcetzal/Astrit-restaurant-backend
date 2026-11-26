package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.OrderDetail;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.dto.SalesReportDto;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.exception.OrderValidationException;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ClientRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.OrderRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            ClientRepository clientRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    public Optional<Order> getOrder(Integer orderId) {
        return orderRepository.getOrder(orderId);
    }

    public Optional<List<Order>> getByClient(Integer clientId) {
        return orderRepository.getByClientId(clientId);
    }

    @Transactional
    public Order save(Order order) {
        validateOrderHasDetails(order);

        Map<Integer, Product> resolvedProducts = new HashMap<>();
        BigDecimal orderTotal = BigDecimal.ZERO;

        for (OrderDetail detail : order.getDetails()) {
            Product product = validateAndFetchProduct(detail);
            resolvedProducts.put(product.getProductId(), product);

            BigDecimal lineSubtotal = calculateAndApplyDetailPricing(detail, product);
            orderTotal = orderTotal.add(lineSubtotal);
        }

        double calculatedTotal = orderTotal.setScale(2, RoundingMode.HALF_UP).doubleValue();
        order.setTotal(calculatedTotal);

        Order savedOrder = orderRepository.save(order);
        enrichSavedOrder(savedOrder, resolvedProducts, calculatedTotal);
        return savedOrder;
    }

    public boolean delete(int orderId) {
        if (getOrder(orderId).isPresent()) {
            orderRepository.delete(orderId);
            return true;
        }
        return false;
    }

    public SalesReportDto getSalesReport() {
        BigDecimal totalRevenue = orderRepository.getTotalRevenue();
        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }
        long totalOrders = orderRepository.countOrders();
        long totalClients = clientRepository.countClients();
        return new SalesReportDto(totalRevenue.setScale(2, RoundingMode.HALF_UP), totalOrders, totalClients);
    }

    private void validateOrderHasDetails(Order order) {
        if (order == null || order.getDetails() == null || order.getDetails().isEmpty()) {
            throw new OrderValidationException("El pedido debe contener al menos un platillo.");
        }
    }

    private Product validateAndFetchProduct(OrderDetail detail) {
        if (detail.getProductId() == null) {
            throw new OrderValidationException("Cada ítem del pedido debe incluir un productId válido.");
        }
        if (detail.getQuantity() == null || detail.getQuantity() <= 0) {
            throw new OrderValidationException(
                    "La cantidad para el producto " + detail.getProductId() + " debe ser mayor a cero."
            );
        }

        Product product = productRepository.getProduct(detail.getProductId())
                .orElseThrow(() -> new OrderValidationException(
                        "El producto con ID " + detail.getProductId() + " no existe."
                ));

        if (!Boolean.TRUE.equals(product.getAvailable())) {
            throw new OrderValidationException(
                    "El platillo '" + product.getName() + "' (ID " + product.getProductId() + ") no está disponible."
            );
        }

        return product;
    }

    private BigDecimal calculateAndApplyDetailPricing(OrderDetail detail, Product product) {
        BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
        BigDecimal lineSubtotal = unitPrice
                .multiply(BigDecimal.valueOf(detail.getQuantity()))
                .setScale(2, RoundingMode.HALF_UP);

        detail.setProductName(product.getName());
        detail.setUnitPrice(unitPrice.doubleValue());
        detail.setSubtotal(lineSubtotal.doubleValue());
        return lineSubtotal;
    }

    private void enrichSavedOrder(Order savedOrder, Map<Integer, Product> resolvedProducts, double calculatedTotal) {
        if (savedOrder == null) {
            return;
        }
        savedOrder.setTotal(calculatedTotal);
        if (savedOrder.getDetails() == null) {
            return;
        }
        for (OrderDetail detail : savedOrder.getDetails()) {
            Product product = resolvedProducts.get(detail.getProductId());
            if (product != null) {
                detail.setProductName(product.getName());
                detail.setUnitPrice(product.getPrice());
            }
        }
    }
}
