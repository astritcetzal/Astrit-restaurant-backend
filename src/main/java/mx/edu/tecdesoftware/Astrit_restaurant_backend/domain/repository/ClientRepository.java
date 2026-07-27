package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> getAll();
    Optional<Client> getByClientId(Integer clientId);
    Client save(Client client);
    void delete(int clientId);
}
