package com.allen.product.application.usecase;

import com.allen.product.domain.model.Product;
import com.allen.product.domain.model.Stock;

import java.util.List;
import java.util.Optional;

public interface ProductUseCase {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    void deleteProduct(Long id);
    Product updateProduct(Long productId, Product updatedProductData);
}