package mx.edu.tecdesoftware.Astrit_restaurant_backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Client;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ClientRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Tag(name="Clients", description="Information of the clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    @Operation(
            summary = "Get all clients",
            description="Return a List of all available clients"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Seccessfull retrieval of clients"
                    )

    @ApiResponse(
            responseCode = "500",
            description="Internal server error"
    )
    public ResponseEntity<List<Client>>  getAll(){
        return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{clientId}")
    @Operation(
            summary = "Get client by ID",
            description = "Return a client by its ID if the ID it existes"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Client found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Client not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<Client> getByCliente(@Parameter(description = "ID of the client retrieved", example="7", required = true) @PathVariable("clientId") Integer clientId){
        return clientService.getClient(clientId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @Operation(
            summary = "Save a new client",
            description ="Register a new client and return the created product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name ="Example product",
                                    value = """
                                            {
                                            "name":"Miranda Ramirez"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Client created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid client data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Client confilct (diplicate code)")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Client> save(@RequestBody Client client){
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @Operation(summary="Delet a client by Id", description ="Delete a client if it exists")
    @ApiResponse(responseCode = "201", description = "Client delete successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
        @ApiResponse(responseCode = "403", description = "Forbidden")
    public ResponseEntity delete(
            @Parameter(description = "Id of the client to be deleted", example="7", required = true)
            @PathVariable("id") Integer clientId){
        if (clientService.delete(clientId)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
