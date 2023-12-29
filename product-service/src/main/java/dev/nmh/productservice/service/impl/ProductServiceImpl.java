package dev.nmh.productservice.service.impl;

import dev.nmh.productservice.entity.Product;
import dev.nmh.productservice.infrastructure.exception.ResponseModel;
import dev.nmh.productservice.model.request.CreateProductRequest;
import dev.nmh.productservice.repository.ProductRepository;
import dev.nmh.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponseModel getAllProducts() {
        return new ResponseModel(HttpStatus.OK, LocalDateTime.now(), "Products retrieved successfully", null, productRepository.getAllProducts());
    }

    @Override
    public ResponseModel createProduct(CreateProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        productRepository.save(product);
        return new ResponseModel(HttpStatus.CREATED, LocalDateTime.now(), "Product created successfully", null, null);
    }

}
