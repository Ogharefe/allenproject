package com.allen.sales.application.usecase;

import com.allen.sales.domain.model.ProductSnapshot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductSnapshotUseCase {
    void upsert(ProductSnapshot snapshot);

    Optional<ProductSnapshot> findBySku(String sku);

    Optional<ProductSnapshot> findByProductId(UUID productId);

    ProductSnapshot getRequiredBySku(String sku);
    void deleteByProductId(UUID productId);
    List<ProductSnapshot> findAll();
}
