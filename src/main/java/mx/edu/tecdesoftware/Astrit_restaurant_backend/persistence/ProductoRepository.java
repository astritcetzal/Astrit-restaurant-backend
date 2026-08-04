package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ProductRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud.ProductoCrudRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Producto;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository
{
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper productMapper;
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }
    public Optional<List<Product>> getByCategory(Integer categoryId)
    {
        List<Producto> productos=productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }
    public Optional<Product> getProduct(int productId){
        return productoCrudRepository.findById(productId)
                .map(producto -> productMapper.toProduct(producto));
    }

    /*
    INSERT INTO producto (nombre, cantidad_stock, estado, id_categoria)
    VALUES (?,?,?,?);
    */
    //guardar un producto
    public Product save(Product product){
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }
    //Eliminar por iD
    public void delete(int idProducto){
        productoCrudRepository.deleteById(idProducto);
    }

    public Optional<Product> getProductByName(String name){
        return productoCrudRepository.findByNombre(name)
                .map(producto -> productMapper.toProduct(producto));
    }


}
