package dev.nmh.productservice.controller;

import dev.nmh.productservice.infrastructure.exception.ResponseModel;
import dev.nmh.productservice.model.request.CreateProductRequest;
import dev.nmh.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        ResponseModel responseModel = productService.getAllProducts();
        return new ResponseEntity<>(responseModel, responseModel.getStatusCode());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid CreateProductRequest request) {
        ResponseModel responseModel = productService.createProduct(request);
        return new ResponseEntity<>(responseModel, responseModel.getStatusCode());
    }

}
