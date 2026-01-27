package com.allen.product.infrastructure.persistence.adapters;

import com.allen.product.domain.model.Product;
import com.allen.product.domain.port.ProductRepositoryPort;
import com.allen.product.infrastructure.persistence.entity.ProductEntity;
import com.allen.product.infrastructure.persistence.mapper.ProductMapper;
import com.allen.product.infrastructure.persistence.springdataJpaRepository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Lazy
    public ProductRepositoryImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findAllProduct() {
        List<ProductEntity> products = productRepository.findAll();

        // Ensure 'products' is not null or empty
        if (products == null || products.isEmpty()) {
            throw new RuntimeException("No products found in the database.");
        }

        List<Product> productList = products.stream()
                .map(productMapper::productEntityToProduct) // Use method reference for clarity
                .collect(Collectors.toList()); // Collect results into a list*/
        return productList;
    }

    @Override
    public Optional<Product> findByProductId(Long id) {
        try {
            return productRepository.findById(id)
                    .map(product -> productMapper.productEntityToProduct(product));
        } catch (Exception e) {
            throw new RuntimeException("Error finding product with id: " + id, e);
        }
    }
    @Override
    public Product saveProduct(Product product) {

        ProductEntity productEntity = productMapper.productToEntity(product);
        return productMapper.productEntityToProduct(productRepository.save(productEntity));
    }

    @Override
    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting product with id: " + id, e);
        }
    }

    @Override
    public boolean existsBySku(String sku) {
        // Break the circular dependency by adding a try-catch block
        try {
            return productRepository.existsBySku(sku);
        } catch (StackOverflowError e) {
            // Log the error if needed
            return false; // Default return value to break the recursion
        }
    }
}
