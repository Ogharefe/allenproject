package com.allen.sales.infrastructure.persistence.japRepository;

import com.allen.sales.infrastructure.persistence.entity.ProductSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductSnapshotRepository extends JpaRepository<ProductSnapshotEntity, UUID> {

    Optional<ProductSnapshotEntity> findBySku(String sku);
}