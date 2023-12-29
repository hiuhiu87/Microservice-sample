package dev.nmh.productservice.service;

import dev.nmh.productservice.infrastructure.exception.ResponseModel;
import dev.nmh.productservice.model.request.CreateProductRequest;

public interface ProductService {

    ResponseModel getAllProducts();

    ResponseModel createProduct(CreateProductRequest request);

}
