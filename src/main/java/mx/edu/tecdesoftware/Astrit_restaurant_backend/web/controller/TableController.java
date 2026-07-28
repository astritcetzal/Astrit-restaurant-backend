package mx.edu.tecdesoftware.Astrit_restaurant_backend.web.controller;

import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Table;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.ProductService;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.TableService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @GetMapping("/")
    public ResponseEntity<List<Table>> getAll(){
        return new ResponseEntity<>(tableService.getAll(), HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Table> getTable(@PathVariable("id") Integer tableId){
        return tableService.getTable(tableId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/")
    public ResponseEntity<Table> save(@RequestBody Table table){
        return new ResponseEntity<>(tableService.save(table), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer tableId){
        if (tableService.delete(tableId)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
