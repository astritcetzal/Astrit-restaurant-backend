package mx.edu.tecdesoftware.Astrit_restaurant_backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary="Get all orders"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfull retrieval of products"
    )

    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<Order>> getAll(){
            return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    @Operation(
            summary = "Get orders form clients"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Client found in the data base"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Client not found in data base"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<Order>> getByClient(@PathVariable @Parameter( description = "clientId", example="2", required = true) Integer clientId ){
        return orderService.getByClient(clientId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/")
    @Operation(
            summary="Save a new order",
            description="Register a  new order and return the created order",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example order",
                                    value= """
                                           {
                                           "clientId":2,
                                           "tableId": 6,
                                           "paymentMethod": "E",
                                           "details":[
                                                {
                                                  "productId": 3,
                                                  "amount": 2,
                                                  "total": 70
                                                },
                                                {
                                                  "productId": 1,
                                                  "amount": 1,
                                                  "total": 85
                                                }
                                           ]
                                           }
                                           """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "order created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid order data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Order conflict (duplicate)")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Order> save(@RequestBody Order order){
        return new ResponseEntity<>(orderService.save(order),HttpStatus.CREATED);
    }

     @DeleteMapping("/{id}")
     @Operation(summary  ="Delete a order by ID", description = "Delete a order if it exists")
     @ApiResponse(responseCode = "200", description = "Order delete successfully")
     @ApiResponse(responseCode = "400", description = "Invalid order data")
     @ApiResponse(responseCode = "401", description = "Unauthorized")
     @ApiResponse(responseCode = "403", description = "Forbidden")

     public ResponseEntity delete(
             @Parameter(description = "ID of the order to be deleted", example="8", required = true)
             @PathVariable("id") Integer orderId){
        if(orderService.delete(orderId)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
         }
     }
}
