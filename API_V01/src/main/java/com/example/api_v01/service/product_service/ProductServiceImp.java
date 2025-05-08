package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.dto.ProductStockDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Admin;
import com.example.api_v01.model.Product;
import com.example.api_v01.repository.ProductRepository;
import com.example.api_v01.service.admin_service.AdminService;
import com.example.api_v01.utils.ExceptionMessage;
import com.example.api_v01.utils.ProductMovement;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService , ExceptionMessage {

    private final ProductRepository productRepository;
    private final AdminService adminService;
    @Override
    public Product saveProduct(UUID id_admin,ProductDTO productDTO) throws NotFoundException {
        Admin admin = adminService.findById(id_admin);
        Product product = ProductMovement.createProductAndStock(admin,productDTO);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID id) throws NotFoundException {
        if(!productRepository.existsById(id)){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProducts() {
        return  productRepository.findAll();
    }

    @Override
    public Product getProduct(UUID id) throws NotFoundException{
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        return product.get();
    }

    @Override
    public Product updateProduct(UUID id, ProductDTO productDTO) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        Product existingProduct = product.get();
        Product ProductValidation = ProductMovement.ValidateProduct(existingProduct,productDTO);
        return productRepository.save(ProductValidation);
    }

    //Esto es un metodo que se utiliza en ProductServiceImp
    @Override
    public void product_stockDelete(UUID id_stock) throws NotFoundException {
        Optional<Product>Optional=productRepository.findProductById_Stock(id_stock);
        if(!Optional.isPresent()){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        Product product=Optional.get();
        product.setStock(null);
        productRepository.save(product);
    }

    @Override
    public List<ProductStockDTO> getProductByCategory(String categoria) throws NotFoundException {
        return List.of();
    }

}
