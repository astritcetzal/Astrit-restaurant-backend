package mx.edu.tecdesoftware.Astrit_restaurant_backend.web.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.Product;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.repository.ProductRepository;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name="Product", description="Manage products in the store")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    @Operation(
            summary = "Get all products",
            description = "Return a List of all available products"
    )// que valor esperas que te responda
    @ApiResponse(
            responseCode = "200",
            description = "Successfull retrieval of products"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )

    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);

    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Return a product by its ID id it exists"
    )
    // que valor esperas que te responda
    @ApiResponse(
            responseCode = "200",
            description = "Product found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Product not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<Product> getProduct(@Parameter(description = "ID of the product retrieved", example="1", required=true)  @PathVariable("id") Integer productId){
        return productService.getProduct(productId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Get a product by category",
            description = "Return all product in a specific category"
    )
    @ApiResponse(responseCode = "200", description = "Product found in the category")
    @ApiResponse(responseCode = "404", description = "Product not found in the category")
    @ApiResponse(responseCode = "500", description = "Internal server")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable @Parameter(description = "Category ID", example = "2", required = true)
                                                           int categoryId){
    return productService.getByCategory(categoryId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @Operation(
            summary="Save a new product",
            description = "Register a new product and return the created product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example product",
                                    value = """
                                            {
                                            "name":"Mirinda",
                                            "categoryId": "3",
                                            "price":"20.5",
                                            "active" : true
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Product conflict (duplicate code or SKU)")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Product> save(@RequestBody Product product){
        Product savedProduct = productService.save(product);

        if (savedProduct == null) {
            // Si es nulo, significa que el nombre ya existía. Lanzamos 409 Conflict.
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Si todo salió bien, devolvemos el producto creado y el 201 Created.
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @Operation(summary  ="Delete a product by ID", description = "Delete a product if it exists")
    @ApiResponse(responseCode = "200", description = "Product delete successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")

    public ResponseEntity delete(@PathVariable("id") Integer productId){
        if (productService.delete(productId)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
