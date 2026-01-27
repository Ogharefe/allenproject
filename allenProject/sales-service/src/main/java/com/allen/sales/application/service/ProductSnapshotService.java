package com.allen.sales.application.service;

import com.allen.sales.application.usecase.ProductSnapshotUseCase;
import com.allen.sales.domain.model.ProductSnapshot;
import com.allen.sales.domain.port.ProductSnapshotPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductSnapshotService implements ProductSnapshotUseCase {

    private final ProductSnapshotPort productSnapshotPort;

    public ProductSnapshotService(ProductSnapshotPort productSnapshotPort) {
        this.productSnapshotPort = productSnapshotPort;
    }

    @Override
    public void upsert(ProductSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "snapshot must not be null");
        Objects.requireNonNull(snapshot.productId(), "snapshot.productId must not be null");
        Objects.requireNonNull(snapshot.sku(), "snapshot.sku must not be null");
        Objects.requireNonNull(snapshot.name(), "snapshot.name must not be null");
        Objects.requireNonNull(snapshot.unitPrice(), "snapshot.unitPrice must not be null");

        productSnapshotPort.upsert(snapshot);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<ProductSnapshot> findBySku(String sku) {
        if (sku == null || sku.isBlank()) {
            return Optional.empty();
        }
        return productSnapshotPort.findBySku(sku);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<ProductSnapshot> findByProductId(UUID productId) {
        if (productId == null) {
            return Optional.empty();
        }
        return productSnapshotPort.findByProductId(productId);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public ProductSnapshot getRequiredBySku(String sku) {
        return findBySku(sku)
                .orElseThrow(() -> new IllegalStateException("ProductSnapshot not found for sku=" + sku));
    }

    @Override
    public void deleteByProductId(UUID productId) {
        if (productId == null) {
            return;
        }
        productSnapshotPort.deleteByProductId(productId);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductSnapshot> findAll() {
        return productSnapshotPort.findAll();
    }
}