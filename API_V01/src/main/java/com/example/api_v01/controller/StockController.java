package com.example.api_v01.controller;

import com.example.api_v01.dto.response.ProductWithStockDTO;
import com.example.api_v01.dto.response.StockChangeRequestDTO;
import com.example.api_v01.dto.response.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.enums.Category;
import com.example.api_v01.service.product_stock_service.ProductStockService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("stock")
public class StockController {

    private final ProductStockService productStockService;

    public StockController(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllStocks() {
        SuccessMessage<List<ProductWithStockDTO>> successMessage = SuccessMessage.<List<ProductWithStockDTO>>builder()
                .status(HttpStatus.OK)
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStock())
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/list/category")
    public ResponseEntity<?> getAllStocksCategory(@RequestParam Category category) throws NotFoundException {
        SuccessMessage<List<ProductWithStockDTO>> successMessage = SuccessMessage.<List<ProductWithStockDTO>>builder()
                .status(HttpStatus.OK)
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStockByCategory(category))
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/list/nameProduct")
    public ResponseEntity<?> getAllStocksNameProduct(@RequestParam String name) throws NotFoundException {
        SuccessMessage<List<ProductWithStockDTO>> successMessage = SuccessMessage.<List<ProductWithStockDTO>>builder()
                .status(HttpStatus.OK)
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStockByNameProduct(name))
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @GetMapping("/{id_stock}")
    public ResponseEntity<?> getStock(@PathVariable UUID id_stock) throws NotFoundException {
        ProductWithStockDTO productStock = productStockService.getProductStockById(id_stock);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK)
                .message("Stock encontrado!!")
                .data(productStock)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    @PostMapping("/clean/{id_stock}")
    public ResponseEntity<?> cleanStock(@PathVariable UUID id_stock) throws NotFoundException {
        ProductWithStockDTO stock = productStockService.cleanStockById(id_stock);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK)
                .message("Stock encontrado!!")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @PostMapping("/discount")
    public ResponseEntity<?> discountStock(StockChangeRequestDTO stockChangeRequestDTO) throws NotFoundException,BadRequestException {
        ProductWithStockDTO stock = productStockService.discountStockById(stockChangeRequestDTO);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK)
                .message("Se desconto el monto del stock")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    @PostMapping("/increase")
    public ResponseEntity<?> increaseStock(StockChangeRequestDTO stockChangeRequestDTO) throws NotFoundException{
        ProductWithStockDTO stock = productStockService.increaseStockById(stockChangeRequestDTO);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK)
                .message("Se aumento el monto del stock")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //MANDA UN JSON JUNTO CON EL ID DEL PRODUCTO PARA ACTUALIZAR
    @PatchMapping("/")
    public ResponseEntity<?> updateProduct(@RequestBody ProductWithStockDTO stockDTO) throws NotFoundException,BadRequestException {
        ProductWithStockDTO stock = productStockService.updateStockById(stockDTO);
        SuccessMessage<ProductWithStockDTO>successMessage= SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK)
                .message("Se actualizo el producto correctamente")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }
}
