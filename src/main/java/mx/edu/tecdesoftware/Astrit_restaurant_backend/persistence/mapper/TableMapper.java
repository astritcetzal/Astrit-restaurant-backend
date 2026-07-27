package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Table;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Mesa;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses={OrderMapper.class})
public interface TableMapper {
    @Mappings({
            @Mapping(source="idMesa",target="tableId"),
            @Mapping(source="numeroMesa", target="tableNumber"),
            @Mapping(source="ubicacion", target="location"),
            @Mapping(source = "estado", target = "active"),
            @Mapping(source = "asientos", target="seats")
    })
    Table toTable(Mesa mesa);
    List<Table> toTables(List<Mesa> mesas);
    @InheritInverseConfiguration
    Mesa toMesa(Table table);
}
