package mx.edu.tecdesoftware.Astrit_restaurant_backend.web.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Table;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping ("/tables")
@Tag(name="Tables", description = "Manage tables in the store")
public class TableController {
    @Autowired
    private TableService tableService;
    @GetMapping("/")
    @Operation(
            summary = "Get all tables",
            description = "Return a List of all available tables"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfull retrieval of tables"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<Table>> getAll(){
        return new ResponseEntity<>(tableService.getAll(), HttpStatus.OK);

    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get table by ID",
            description = "Return a table by its Id if exists"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Table found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Table not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<Table> getTable(@Parameter(description = "ID of the table retrieved", example = "4", required = true) @PathVariable("id") Integer tableId){
        return tableService.getTable(tableId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/")
    @Operation(
            summary = "Save a new table",
            description = "Register a new table and return the created table",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example product",
                                    value = """
                                            {
                                            "tableNumber":8,
                                            "location": "Ventana de la puerta principal",
                                            "seats": 5,
                                            "active" : true
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Table created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid table data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Table conflict (duplicate code or SKU)")
    @ApiResponse(responseCode = "500", description = "Internal server error")

    public ResponseEntity<Table> save(@RequestBody Table table){
        return new ResponseEntity<>(tableService.save(table), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @Operation(summary  ="Delete a table by ID", description = "Delete a table if it exists")
    @ApiResponse(responseCode = "200", description = "Table delete successfully")
    @ApiResponse(responseCode = "400", description = "Invalid table data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")

    public ResponseEntity delete(@Parameter(description = "ID of the table to be deleted", example="3", required = true ) @PathVariable("id") Integer tableId){
        if (tableService.delete(tableId)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
