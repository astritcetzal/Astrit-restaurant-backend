package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Category;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel ="spring")
public interface CategoryMapper{
    @Mappings({
            @Mapping(source="idCategoria", target="categoryId"),
            @Mapping(source="descripcion", target="category"),
            @Mapping(source="estado", target="active")
    })

    Category toCategory(Categoria categoria);
    List<Category> toCategories(List<Categoria> categoria);

    @InheritInverseConfiguration
    Categoria toCategoria(Category category);

}

