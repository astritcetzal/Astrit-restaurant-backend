package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Table;
import java.util.List;
import java.util.Optional;

public interface TableRepository {
    List<Table> getAll();
    Table save(Table table);
    Optional<Table> getById(int tableId) ;
    void delete(int tableId);
}
