package com.example.api_v01.service.product_service;

import com.example.api_v01.dto.entityLike.ProductDTO;

import com.example.api_v01.dto.response.ProductResponseDTO;
import com.example.api_v01.handler.NotFoundException;
import com.example.api_v01.model.Admin;
import com.example.api_v01.model.Product;
import com.example.api_v01.model.enums.Category;
import com.example.api_v01.repository.ProductRepository;
import com.example.api_v01.service.admin_service.AdminService;
import com.example.api_v01.utils.ExceptionMessage;
import com.example.api_v01.utils.ProductMovement;
import com.example.api_v01.utils.Tuple;
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
    public Tuple<ProductResponseDTO,UUID> saveProduct(
            UUID id_admin,
            ProductDTO productDTO
    ) throws NotFoundException {
        Admin admin = adminService.findById(id_admin);
        Product productAdd = productRepository.save(ProductMovement.createProductAndStock(admin,productDTO));
        return new Tuple<>(ProductMovement.moveProductResponseDTO(productAdd),productAdd.getId_Product());
    }

    @Override
    public void deleteProduct(UUID id) throws NotFoundException {
        if(!productRepository.existsById(id)){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getProducts() {
        return ProductMovement.ListProductDTO(productRepository.findAll());
    }

    @Override
    public ProductDTO getProductDTO(UUID id) throws NotFoundException{
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        return ProductMovement.moveProductDTO(product.get());
    }

    @Override
    public Product getProduct(UUID id) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        return product.get();
    };

    @Override
    public ProductResponseDTO updateProduct(UUID id, ProductDTO productDTO) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()){
            throw new NotFoundException(PRODUCT_NOT_FOUND);
        }
        Product existingProduct = product.get();
        Product ProductValidation =  productRepository.save(ProductMovement.ValidateProduct(existingProduct,productDTO)) ;
        return ProductMovement.moveProductResponseDTO(ProductValidation);
    }

    @Override
    public List<ProductDTO> getProductByCategory(Category categoria) throws NotFoundException {
        List<Product> ListProduct= productRepository.findProductsByCategory(categoria)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
        return ProductMovement.ListProductDTO(ListProduct);
    }

    @Override
    public List<ProductDTO> getProductByName(String name) throws NotFoundException {
        List<Product> ListProduct= productRepository.findProductByName(name)
                .orElseThrow(() -> new NotFoundException(NAME_PRODUCT_NOT_FOUND));
        return ProductMovement.ListProductDTO(ListProduct);
    }

}
