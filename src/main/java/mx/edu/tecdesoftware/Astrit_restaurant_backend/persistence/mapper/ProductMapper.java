package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Producto;
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
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "disponible", target = "available"),
            @Mapping(source = "categoria", target = "category")
    })
        //este archivo traduce variables de base de datos
    Product toProduct (Producto producto);
    List<Product> toProducts(List<Producto> productos);
    @Mappings({
            @Mapping(source = "productId", target = "idProducto"),
            @Mapping(source = "name", target = "nombre"),
            @Mapping(source = "categoryId", target = "idCategoria"),
            @Mapping(source = "price", target = "precio"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "available", target = "disponible"),
            @Mapping(source = "category", target = "categoria")
    })
    Producto toProducto(Product product);
}
