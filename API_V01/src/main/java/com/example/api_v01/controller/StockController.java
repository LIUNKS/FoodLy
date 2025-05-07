package com.example.api_v01.controller;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.dto.ProductStockDTO;
import com.example.api_v01.dto.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ProductStock;
import com.example.api_v01.service.product_stock_service.ProductStockService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final ProductStockService productStockService;

    public StockController(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Operation(
            summary = "DESCONTINUADO",
            description = "No usar"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro producto",
                    content = @Content
            )
    })
    //Posiblemente se descontinue
    @PostMapping("/{id_product}")
    public ResponseEntity<?> CreateStock(@PathVariable UUID id_product,@RequestBody ProductStockDTO productStockDTO) throws NotFoundException {
        ProductStock productStock = productStockService.saveProductStock(id_product,productStockDTO);
        SuccessMessage<ProductStock>successMessage = SuccessMessage.<ProductStock>builder()
                .status(HttpStatus.CREATED)
                .message("Se creo y asigno correctamente el stock al producto indicado")
                .data(productStock)
                .build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id_stock}")
                .buildAndExpand(productStock.getId_product_stock())
                .toUri();
        return ResponseEntity.created(location).body(successMessage);
    }

    @Operation(
            summary = "Obtiene todos los stocks",
            description = "Este endpoint lista stock de todos los productos disponibles en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron stocks",
                    content = @Content
            )
    })
    @GetMapping("/list")
    public ResponseEntity<?> getAllStocks() {
        SuccessMessage<List<ProductStock>> successMessage = SuccessMessage.<List<ProductStock>>builder()
                .status(HttpStatus.OK)
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStock())
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @Operation(
            summary = "Obtiene stock por ID",
            description = "Este endpoint obtiene stock de un producto disponible en el sistema indicando UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro stock",
                    content = @Content
            )
    })
    @GetMapping("/{id_stock}")
    public ResponseEntity<?> getStock(@PathVariable UUID id_stock) throws NotFoundException {
        ProductStock productStock = productStockService.getProductStockById(id_stock);
        SuccessMessage<ProductStock>successMessage = SuccessMessage.<ProductStock>builder()
                .status(HttpStatus.OK)
                .message("Stock encontrado!!")
                .data(productStock)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @Operation(
            summary = "Elimina stock por ID",
            description = "Este endpoint elimina stock de un producto disponible en el sistema indicando UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro stock",
                    content = @Content
            )
    })
    @DeleteMapping("/{id_stock}")
    public ResponseEntity<?> deleteStock(@PathVariable UUID id_stock) throws NotFoundException {
        productStockService.deleteProductStock(id_stock);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Limpia stock por ID",
            description = "Este endpoint limpia stock del producto disponible en el sistema indicando UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro stock",
                    content = @Content
            )
    })
    @PostMapping("/clean/{id_stock}")
    public ResponseEntity<?> cleanStock(@PathVariable UUID id_stock) throws NotFoundException {
        ProductStock stock = productStockService.cleanStockById(id_stock);
        SuccessMessage<ProductStock>successMessage = SuccessMessage.<ProductStock>builder()
                .status(HttpStatus.OK)
                .message("Stock encontrado!!")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @Operation(
            summary = "Descuenta stock por ID",
            description = "Este endpoint descuenta stock del producto disponible en el sistema indicando UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro stock",
                    content = @Content
            )
    })
    @PostMapping("/discount/{id_stock}")
    public ResponseEntity<?> discountStock(@PathVariable UUID id_stock,@RequestParam Integer count) throws NotFoundException,BadRequestException {
        ProductStock stock = productStockService.discountStockById(id_stock,count);
        SuccessMessage<ProductStock>successMessage = SuccessMessage.<ProductStock>builder()
                .status(HttpStatus.OK)
                .message("Se desconto el monto del stock")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @Operation(
            summary = "Incrementar stock de un producto por ID",
            description = "Este endpoint incrementa stock del producto disponible en el sistema indicando UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontro stock",
                    content = @Content
            )
    })
    @PostMapping("/increase/{id_stock}")
    public ResponseEntity<?> increaseStock(@PathVariable UUID id_stock,@RequestParam Integer count) throws NotFoundException{
        ProductStock stock = productStockService.increaseStockById(id_stock,count);
        SuccessMessage<ProductStock>successMessage = SuccessMessage.<ProductStock>builder()
                .status(HttpStatus.OK)
                .message("Se aumento el monto del stock")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //Falta implementar
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) throws NotFoundException {
        return null;
    }


    @Operation(
            summary = "Obtener stock por ID",
            description = "Este endpoint lanza una excepción si no se proporciona el ID necesario para buscar el stock."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "No se proporcionó el ID del stock. Es obligatorio para la búsqueda.")
    })
    @GetMapping("/")
    public ResponseEntity<?> getStockId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del stock para buscarlo");
    }

    @Operation(
            summary = "Actualizar stock",
            description = "Este endpoint lanza una excepción si no se proporciona el ID necesario para actualizar el stock."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "No se proporcionó el ID del stock. Es obligatorio para actualizarlo.")
    })
    @PutMapping("/")
    public ResponseEntity<?> updateStockId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del stock para actualizarlo");
    }

    @Operation(
            summary = "Eliminar stock",
            description = "Este endpoint lanza una excepción si no se proporciona el ID necesario para eliminar el stock."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "No se proporcionó el ID del stock. Es obligatorio para eliminarlo.")
    })
    @DeleteMapping("/")
    public ResponseEntity<?> deleteStockId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del stock para eliminarlo");
    }
}
