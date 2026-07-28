package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    public List<Client> getAll(){
        return clientRepository.getAll();
    }
    public Optional<Client> getClient(Integer idCliente){
        return clientRepository.getByClientId(idCliente);
    }

    public Client save(Client client){

        return clientRepository.save(client);
    }
    public boolean delete(int clientId){
        if(getClient(clientId).isPresent()){
            clientRepository.delete(clientId);
            return true;
        }else{
            return false;
        }
    }
}
