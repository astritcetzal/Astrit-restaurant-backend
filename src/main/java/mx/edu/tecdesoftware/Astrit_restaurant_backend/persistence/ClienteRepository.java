package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ClientRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud.ClienteCrudRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Cliente;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository implements ClientRepository {
    @Autowired
    private ClienteCrudRepository clienteCrudRepository;
    @Autowired
    private ClientMapper clientMapper;

    public List<Client> getAll(){
        List<Cliente> clientes = (List<Cliente>) clienteCrudRepository.findAll();
        return clientMapper.toClients(clientes);
    }
    public Optional<Client> getByClientId(Integer idCliente){
        return clienteCrudRepository.findById(idCliente).map(cliente -> clientMapper.toClient(cliente));
    }
    public Client save(Client client){
        Cliente cliente = clientMapper.toCliente(client);
        return clientMapper.toClient(clienteCrudRepository.save(cliente));
    }
    public void delete(int clientId){
        clienteCrudRepository.deleteById(clientId);
    }

}
