package com.example.api_v01.controller;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.dto.ProductStockDTO;
import com.example.api_v01.dto.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.ProductStock;
import com.example.api_v01.service.product_stock_service.ProductStockService;

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

    @GetMapping("/list")
    public ResponseEntity<?> getAllStocks() {
        SuccessMessage<List<ProductStock>> successMessage = SuccessMessage.<List<ProductStock>>builder()
                .status(HttpStatus.OK)
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStock())
                .build();
        return ResponseEntity.ok(successMessage);
    }

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


    @DeleteMapping("/{id_stock}")
    public ResponseEntity<?> deleteStock(@PathVariable UUID id_stock) throws NotFoundException {
        productStockService.deleteProductStock(id_stock);
        return ResponseEntity.noContent().build();
    }

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

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody ProductStockDTO stockDTO) throws NotFoundException,BadRequestException {
        ProductStock stock = productStockService.updateStockById(id,stockDTO);
        SuccessMessage<ProductStock>successMessage= SuccessMessage.<ProductStock>builder()
                .status(HttpStatus.OK)
                .message("Se actualizo el producto correctamente")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @GetMapping("/")
    public ResponseEntity<?> getStockId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del stock para buscarlo");
    }

    @PutMapping("/")
    public ResponseEntity<?> updateStockId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del stock para actualizarlo");
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteStockId() throws BadRequestException {
        throw new BadRequestException("Se necesita el ID del stock para eliminarlo");
    }
}
