package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Table;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.TableRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.crud.MesaCrudRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity.Mesa;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MesaRepository implements TableRepository {
    @Autowired
    private MesaCrudRepository mesaCrudRepository;
    @Autowired
    private TableMapper tableMapper;

    public List<Table> getAll(){
            List<Mesa> mesas= (List<Mesa>) mesaCrudRepository.findAll();
            return tableMapper.toTables(mesas);
    }
    public Optional<Table> getById(int tableId){
       return mesaCrudRepository.findById(tableId).map(mesa -> tableMapper.toTable(mesa));

    }
    public Table save(Table table){
        Mesa mesa = tableMapper.toMesa(table);
        return tableMapper.toTable(mesaCrudRepository.save(mesa));
    }

    public void delete(int tableId){
       mesaCrudRepository.deleteById(tableId);
    }
}
