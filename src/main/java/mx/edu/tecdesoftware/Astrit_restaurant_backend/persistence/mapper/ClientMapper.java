package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    //PEDIDIO EN BD, NO OLVIDAR
    @Mappings({
            @Mapping(source="idCliente", target="clientId"),
            @Mapping(source="nombre", target="name")
    })
    Client toClient(Cliente cliente);
        List<Client> toClients(List<Cliente> clientes);

}
