package com.allen.product.domain.port;

import com.allen.product.domain.model.StockTransaction;

import java.util.List;

public interface StockTransactionRepositoryPort {

    StockTransaction saveTransaction(StockTransaction transaction);
    List<StockTransaction> findByProductId(Long productId);
    List<StockTransaction> findByWarehouseId(Long warehouseId);
    List<StockTransaction> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
    List<StockTransaction> findByProductSku(String productSku);
    List<StockTransaction> getAllTransactions();

}
