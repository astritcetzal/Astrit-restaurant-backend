package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Role;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Cliente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface ClientMapper {
    //PEDIDIO EN BD, NO OLVIDAR
    @Mappings({
            @Mapping(source="idCliente", target="clientId"),
            @Mapping(source="nombre", target="name"),
            @Mapping(target="role", expression="java(mapRole(cliente.getRol()))"),
            @Mapping(source = "pedidos", target = "order")
    })
    Client toClient(Cliente cliente);
    List<Client> toClients(List<Cliente> clientes);
    @InheritInverseConfiguration
    @Mapping(target = "rol", expression = "java(mapRoleToString(client.getRole()))")
    @Mapping(target = "pedidos", ignore=true)
    Cliente toCliente(Client client);

    default Role mapRole(String rol) {
        return Role.fromString(rol);
    }

    default String mapRoleToString(Role role) {
        return role != null ? role.name() : Role.CLIENTE.name();
    }

}
