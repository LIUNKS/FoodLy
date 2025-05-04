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

    //Posiblemente deje de estar
    @Override
    public ProductStock saveProductStock(UUID id_product,ProductStockDTO productStock) throws NotFoundException {

        ProductStock stock = ProductStock.builder()
                .ini_stock(productStock.getIni_stock())
                .current_stock(productStock.getCurrent_stock())
                .total_sold(productStock.getTotal_sold())
                .build();

        stock = productStockRepository.save(stock);

        ProductDTO productDTO = ProductDTO.builder()
                .stock(stock)
                .build();

        productService.updateProduct(id_product,productDTO);

        return stock;
    }

    @Override
    public ProductStock getProductStockById(UUID id) throws NotFoundException {
        Optional<ProductStock>productStock = productStockRepository.findById(id);
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
    public ProductStock cleanStockById(UUID id) throws NotFoundException {
        Optional<ProductStock>productStock = productStockRepository.findById(id);
        if (!productStock.isPresent()) {
            throw new NotFoundException(STOCK_NOT_FOUND);
        }
        ProductStock stock = productStock.get();
        stock.setIni_stock(0);
        stock.setCurrent_stock(0);
        stock.setTotal_sold(0);
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
    public ProductStock discountStockById(UUID id, Integer count) throws NotFoundException , BadRequestException {
        Optional<ProductStock>productStock = productStockRepository.findById(id);

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
    public ProductStock updateStockById(UUID id, ProductStockDTO productStockDTO) throws NotFoundException {

        Optional<ProductStock>stockOptional = productStockRepository.findById(id);

        if(!stockOptional.isPresent()){
            throw new NotFoundException(STOCK_NOT_FOUND);
        }

        ProductStock stock = stockOptional.get();

        ProductStock stockValidation = ValidationStock(stock,productStockDTO);

        productStockRepository.save(stockValidation);

        return stock;
    }

    private ProductStock ValidationStock(ProductStock stock,ProductStockDTO productStockDTO){
        if( productStockDTO.getIni_stock() != null && productStockDTO.getIni_stock() >=0 ){
            stock.setIni_stock(productStockDTO.getIni_stock());
        }
        if( productStockDTO.getCurrent_stock() != null && productStockDTO.getCurrent_stock() >=0 ){
            stock.setCurrent_stock(productStockDTO.getCurrent_stock());
        }
        if( productStockDTO.getTotal_sold() != null && productStockDTO.getTotal_sold() >= 0 ){
            stock.setTotal_sold(productStockDTO.getTotal_sold());
        }
        return stock;
    }

}
