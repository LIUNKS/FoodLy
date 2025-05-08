package com.example.api_v01.service.product_stock_service;

import com.example.api_v01.controller.StockController;
import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.dto.ProductStockDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.ProductStock;
import com.example.api_v01.repository.ProductStockRepository;
import com.example.api_v01.service.product_service.ProductService;
import com.example.api_v01.utils.ExceptionMessage;
import com.example.api_v01.utils.InventoryMovement;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductStockServiceImp implements ProductStockService, ExceptionMessage {

    private final ProductStockRepository productStockRepository;

    private final ProductService productService;

    @Override
    public ProductStock getProductStockById(UUID id_productStock) throws NotFoundException {
        Optional<ProductStock>productStock = productStockRepository.findById(id_productStock);
        if(!productStock.isPresent()){
            throw new NotFoundException(STOCK_NOT_FOUND);
        }
        return productStock.get();
    }

    @Override
    public List<ProductStock> getAllProductStock() {
        return productStockRepository.findAll();
    }

    @Override
    public void deleteProductStock(UUID id) throws NotFoundException {
        if (!productStockRepository.existsById(id)) {
            throw new NotFoundException(STOCK_NOT_FOUND);
        }
        productService.product_stockDelete(id);
        productStockRepository.deleteById(id);
    }

    @Override
    public ProductStock cleanStockById(UUID id_productStock) throws NotFoundException {
        Optional<ProductStock>productStockOptional = productStockRepository.findById(id_productStock);
        if (!productStockOptional.isPresent()) {
            throw new NotFoundException(STOCK_NOT_FOUND);
        }
        ProductStock stock = InventoryMovement.CleanStock(productStockOptional.get());
        productStockRepository.save(stock);
        return stock;
    }

    @Override
    public ProductStock increaseStockById(UUID id, Integer count) throws NotFoundException {

        Optional<ProductStock>productStock = productStockRepository.findById(id);

        if(!productStock.isPresent()){
            throw new NotFoundException(STOCK_NOT_FOUND);
        }

        ProductStock stock = InventoryMovement.increaseStock(productStock.get(), count);
        productStockRepository.save(stock);
        return stock;
    }

    @Override
    public ProductStock discountStockById(UUID id_productStock, Integer count) throws NotFoundException , BadRequestException {
        Optional<ProductStock>productStock = productStockRepository.findById(id_productStock);

        if(!productStock.isPresent()){
            throw new NotFoundException(STOCK_NOT_FOUND);
        }

        ProductStock stock = InventoryMovement.discountStock(productStock.get(), count);

        if(stock == null) {
            throw new BadRequestException(DISCOUNT_STOCK);
        }

        productStockRepository.save(stock);

        return stock;
    }

    @Override
    public ProductStock updateStockById(UUID id_productStock, ProductStockDTO productStockDTO) throws NotFoundException {

        Optional<ProductStock>stockOptional = productStockRepository.findById(id_productStock);

        if(!stockOptional.isPresent()){
            throw new NotFoundException(STOCK_NOT_FOUND);
        }

        ProductStock stock = stockOptional.get();

        ProductStock stockValidation = InventoryMovement.ValidationStock(stock,productStockDTO);

        productStockRepository.save(stockValidation);

        return stock;
    }


}
