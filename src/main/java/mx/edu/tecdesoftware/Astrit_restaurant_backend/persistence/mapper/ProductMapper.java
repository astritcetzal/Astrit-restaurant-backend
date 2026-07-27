package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
@Mapper(componentModel="spring", uses={CategoryMapper.class})
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "idProducto", target = "productId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "precio", target = "price"),
            @Mapping(source = "categoria", target = "category")
    })
        //este archivo traduce variables de base de datos
    Product toProduct (Producto producto);
    List<Product> toProducts(List<Producto> productos);
    @InheritInverseConfiguration
    Producto toProducto(Product product);
}
