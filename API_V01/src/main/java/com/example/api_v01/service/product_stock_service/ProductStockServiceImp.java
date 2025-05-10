package com.example.api_v01.service.product_stock_service;

import com.example.api_v01.dto.entityLike.ProductStockDTO;
import com.example.api_v01.dto.response.ProductWithStockDTO;
import com.example.api_v01.dto.response.StockChangeRequestDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.ProductStock;
import com.example.api_v01.model.enums.Category;
import com.example.api_v01.repository.ProductRepository;
import com.example.api_v01.repository.ProductStockRepository;
import com.example.api_v01.utils.ExceptionMessage;
import com.example.api_v01.utils.InventoryMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductStockServiceImp implements ProductStockService, ExceptionMessage {

    private final ProductStockRepository productStockRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductWithStockDTO getProductStockById(UUID id_productStock) throws NotFoundException {
        Product product = productRepository.findProductStockById(id_productStock)
                .orElseThrow( () -> new NotFoundException(STOCK_NOT_FOUND));
        return InventoryMovement.getProductWithStockDTO(product);
    }

    @Override
    public List<ProductWithStockDTO> getAllProductStock() {
        return InventoryMovement.getListProductWithStock(productRepository.findAll());
    }

    @Override
    public List<ProductWithStockDTO> getAllProductStockByCategory(Category category) throws NotFoundException {
        List<Product>ListProductByCategory=productRepository.findProductsByCategory(category)
                .orElseThrow( ( ) -> new NotFoundException("Categoria no encontrada") );
        return InventoryMovement.getListProductWithStock(ListProductByCategory);
    }

    @Override
    public List<ProductWithStockDTO> getAllProductStockByNameProduct(String product) throws NotFoundException {
        List<Product>ListProductByName=productRepository.findProductByName(product)
                .orElseThrow( ( ) -> new NotFoundException("producto no encontrada") );
        return InventoryMovement.getListProductWithStock(ListProductByName);
    }

    @Override
    public ProductWithStockDTO cleanStockById(UUID id_productStock) throws NotFoundException {
        Product product = productRepository.findProductStockById(id_productStock)
                .orElseThrow( () -> new NotFoundException(STOCK_NOT_FOUND));
        ProductStock stock = productStockRepository.save(InventoryMovement.CleanStock(product));
        product.setStock(stock);
        return InventoryMovement.getProductWithStockDTO(product);
    }

    @Override
    public ProductWithStockDTO increaseStockById(StockChangeRequestDTO stockChangeRequestDTO) throws NotFoundException {
        Product product = productRepository.findProductStockById(stockChangeRequestDTO.getId_product_stock())
                .orElseThrow( () -> new NotFoundException(STOCK_NOT_FOUND));
        ProductStock stock = InventoryMovement.increaseStock(product, stockChangeRequestDTO.getCount());
        productStockRepository.save(stock);
        product.setStock(stock);
        return InventoryMovement.getProductWithStockDTO(product);
    }

    @Override
    public ProductWithStockDTO discountStockById(StockChangeRequestDTO stockChangeRequestDTO) throws NotFoundException , BadRequestException {
        Product product = productRepository.findProductStockById(stockChangeRequestDTO.getId_product_stock())
                .orElseThrow( () -> new NotFoundException(STOCK_NOT_FOUND));
        ProductStock stock = InventoryMovement.discountStock(product, stockChangeRequestDTO.getCount());
        if(stock == null) {
            throw new BadRequestException(DISCOUNT_STOCK);
        }
        productStockRepository.save(stock);
        product.setStock(stock);
        return InventoryMovement.getProductWithStockDTO(product);
    }


    //No se usa en controlado!!, es usado en un servicio de CustomerOrder NO TOCAR
    @Override
    public ProductWithStockDTO discountStockById(UUID id_productStock, Integer count) throws NotFoundException , BadRequestException {
        Product product = productRepository.findProductStockById(id_productStock)
                .orElseThrow( () -> new NotFoundException(STOCK_NOT_FOUND));
        ProductStock stock = InventoryMovement.discountStock(product, count);
        if(stock == null) {
            throw new BadRequestException(DISCOUNT_STOCK);
        }
        productStockRepository.save(stock);
        product.setStock(stock);
        return InventoryMovement.getProductWithStockDTO(product);
    }

    @Override
    public ProductWithStockDTO updateStockById(ProductWithStockDTO productWithStockDTO) throws NotFoundException {

        Product product = productRepository.findProductStockById(productWithStockDTO.getProduct_stock().getId_productStock())
                .orElseThrow( () -> new NotFoundException(STOCK_NOT_FOUND));

        ProductStock stock = product.getStock();

        ProductStock stockValidation = productStockRepository.save(InventoryMovement.ValidationStock(stock,productWithStockDTO.getProduct_stock()));

        product.setStock(stockValidation);

        return InventoryMovement.getProductWithStockDTO(product);
    }


}
