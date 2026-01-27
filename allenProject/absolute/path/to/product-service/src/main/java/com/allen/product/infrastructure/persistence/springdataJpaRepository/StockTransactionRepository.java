package com.allen.product.infrastructure.persistence.springdataJpaRepository;

import com.allen.product.infrastructure.persistence.entity.StockTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransactionEntity, Long> {
    List<StockTransactionEntity> findByProductId(Long productId);
    List<StockTransactionEntity> findByWarehouseId(Long warehouseId);
    List<StockTransactionEntity> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
    List<StockTransactionEntity> findByProductSku(String productSku);
}