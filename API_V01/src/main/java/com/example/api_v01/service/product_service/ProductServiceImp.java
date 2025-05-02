package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.ProductDTO;
import com.example.api_v01.handler.BadRequestException;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Product;
import com.example.api_v01.repository.ProductRepository;
import com.example.api_v01.utils.ProductMovement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Boolean isExist(UUID id) {
        return productRepository.existsById(id);
    }

    @Override
    public Product saveProduct(ProductDTO productDTO) {
        Product product = ProductMovement.createProductAndStock(productDTO);
        product = productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(UUID id) throws NotFoundException {
        if(!productRepository.existsById(id)){
            throw new NotFoundException("Producto no encontrado");
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
            throw new NotFoundException("Producto no encontrado");
        }
        return product.get();
    }

    @Override
    public Product updateProduct(UUID id, ProductDTO productDTO) throws NotFoundException{
        Optional<Product> product = productRepository.findById(id);

        if(!product.isPresent()){
            throw new NotFoundException("Producto no encontrado mamoso");
        }

        Product existingProduct = product.get();
        // Actualiza solo los campos permitidos
        if (productDTO.getName_product() != null) {existingProduct.setName_product(productDTO.getName_product());}
        if (productDTO.getPrice() != null) {existingProduct.setPrice(productDTO.getPrice());}
        if (productDTO.getAdditional_observation() != null) {existingProduct.setAdditional_observation(productDTO.getAdditional_observation());}
        if (productDTO.getCategory() != null) {existingProduct.setCategory(productDTO.getCategory());}
        if (productDTO.getStock() != null) {existingProduct.setStock(productDTO.getStock());}
        return productRepository.save(existingProduct);
    }

    @Override
    public void product_stockDelete(UUID id_stock) throws NotFoundException {
        Optional<Product>Optional=productRepository.findProductById_Stock(id_stock);
        if(!Optional.isPresent()){
            throw new NotFoundException("Producto no encontrado");
        }
        Product product=Optional.get();
        product.setStock(null);
        productRepository.save(product);
    }
}
