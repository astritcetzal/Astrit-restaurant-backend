package mx.edu.tecdesoftware.Astrit_restaurant_backend.web.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Order;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.OrderRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Orders")
@Tag(name="Order", description="Manage orders in th restaurant")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAll(){
            return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> getByClient(@PathVariable @Parameter( description = "clientId", required = true) Integer clientId ){
        return orderService.getByClient(clientId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/")
    public ResponseEntity<Order> save(@RequestBody Order order){
        return new ResponseEntity<>(orderService.save(order),HttpStatus.CREATED);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer orderId){
        if(orderService.delete(orderId)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
         }
     }
}
