package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Table;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;
    public List<Table> getAll(){
        return tableRepository.getAll();
    }
    public Optional<Table> getTable(Integer tableId){
        return tableRepository.getById(tableId);
    }
    public Table save(Table table){
        return tableRepository.save(table);
    }
    public boolean delete(int tableId){
        if(getTable(tableId).isPresent()){
            tableRepository.delete(tableId);
            return true;
        }
        {
            return false;
        }
    }
}
