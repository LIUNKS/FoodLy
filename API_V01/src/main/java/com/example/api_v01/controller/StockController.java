package com.example.api_v01.controller;

import com.example.api_v01.dto.entityLike.ProductStockDTO;
import com.example.api_v01.dto.response.ProductWithStockDTO;
import com.example.api_v01.dto.response.StockChangeRequestDTO;
import com.example.api_v01.dto.response.SuccessMessage;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.enums.Category;
import com.example.api_v01.service.product_stock_service.ProductStockService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("stock")
@RequiredArgsConstructor
public class StockController {   //CONTROLADOR TESTEADO, LISTO PARA USAR

    private final ProductStockService productStockService;

    //Me devulve la lista de stock con los nombre del producto al que pertenecen
    @GetMapping("/list")
    public ResponseEntity<?> getAllStocks() {
        SuccessMessage<List<ProductWithStockDTO>> successMessage = SuccessMessage.<List<ProductWithStockDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStock())
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //Me devulve la lista de stock con los nombre del producto al que pertenecen por la categoria del producto
    @GetMapping("/list/category")
    public ResponseEntity<?> getAllStocksCategory(@RequestParam Category category) throws NotFoundException {
        SuccessMessage<List<ProductWithStockDTO>> successMessage = SuccessMessage.<List<ProductWithStockDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStockByCategory(category))
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //Me devulve la lista de stock con los nombre del producto al que pertenecen por el nombre del producto
    @GetMapping("/list/nameProduct")
    public ResponseEntity<?> getAllStocksNameProduct(@RequestParam String nameProduct) throws NotFoundException {
        SuccessMessage<List<ProductWithStockDTO>> successMessage = SuccessMessage.<List<ProductWithStockDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("La lista de stock (!puede que la lista este vacia)")
                .data(productStockService.getAllProductStockByNameProduct(nameProduct))
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //me devulve un stock con el nombre del producto al que pertenecen mediante el ID del stock
    @GetMapping("/{id_stock}")
    public ResponseEntity<?> getStock(@PathVariable UUID id_stock) throws NotFoundException {
        ProductWithStockDTO productStock = productStockService.getProductStockById(id_stock);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Stock encontrado!!")
                .data(productStock)
                .build();
        return ResponseEntity.ok(successMessage);
    }

    //limpia el stock a 0 pasandole el id del stock
    @PostMapping("/clean/{id_stock}")
    public ResponseEntity<?> cleanStock(@PathVariable UUID id_stock) throws NotFoundException {
        ProductWithStockDTO stock = productStockService.cleanStockById(id_stock);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Stock encontrado!!")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //me descuenta cierta cantidad de un producto pasandole un dto
    @PostMapping("/discount")
    public ResponseEntity<?> discountStock(@RequestBody StockChangeRequestDTO stockChangeRequestDTO) throws NotFoundException,BadRequestException {
        ProductWithStockDTO stock = productStockService.discountStockById(stockChangeRequestDTO);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Se desconto el monto del stock")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //me aumenta una cierta cantidad de un producto pasandole un dto
    @PostMapping("/increase")
    public ResponseEntity<?> increaseStock(@RequestBody StockChangeRequestDTO stockChangeRequestDTO) throws NotFoundException{
        ProductWithStockDTO stock = productStockService.increaseStockById(stockChangeRequestDTO);
        SuccessMessage<ProductWithStockDTO>successMessage = SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Se aumento el monto del stock")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }

    //MANDA UN JSON JUNTO CON EL ID DEL PRODUCTO PARA ACTUALIZAR
    @PatchMapping("/")
    public ResponseEntity<?> updateProduct(@RequestBody ProductStockDTO stockDTO) throws NotFoundException,BadRequestException {
        ProductWithStockDTO stock = productStockService.updateStockById(stockDTO);
        SuccessMessage<ProductWithStockDTO>successMessage= SuccessMessage.<ProductWithStockDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Se actualizo el producto correctamente")
                .data(stock)
                .build();
        return ResponseEntity.ok().body(successMessage);
    }
}
