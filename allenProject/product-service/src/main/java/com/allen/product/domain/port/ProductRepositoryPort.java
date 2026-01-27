package com.allen.product.domain.port;

import com.allen.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    List<Product> findAllProduct();
    Optional<Product> findByProductId(Long id);
    Product saveProduct(Product product);
    void deleteById(Long id);
    boolean existsBySku(String sku);

}