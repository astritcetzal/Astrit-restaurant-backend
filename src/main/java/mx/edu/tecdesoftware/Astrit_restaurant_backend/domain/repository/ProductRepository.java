package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();
    Optional<List<Product>> getByCategory(Integer categoryId);
    Optional<List<Product>> getScarceProducts(int quantity);
    Optional<Product> getProduct(int productId);
    Product save(Product product);
    void delete(int productoId);
}
