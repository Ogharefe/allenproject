package com.allen.sales.domain.port;

import com.allen.sales.domain.model.ProductSnapshot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductSnapshotPort {

    void upsert(ProductSnapshot snapshot);

    Optional<ProductSnapshot> findBySku(String sku);

    Optional<ProductSnapshot> findByProductId(UUID productId);

    void deleteProductSnapshot(UUID id);

    List<ProductSnapshot> findAll();

    default void deleteByProductId(UUID productId) {
        deleteProductSnapshot(productId);
    }
}