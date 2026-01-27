package com.allen.product.application.usecase;

import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.product.domain.model.Stock;
import com.allen.product.domain.model.StockTransaction;

import java.util.List;

public interface StockTransactionUseCase {

    StockTransaction save(StockTransaction transaction);
    List<StockTransaction> findByProductId(Long productId);
    List<StockTransaction> findByWarehouseId(Long warehouseId);
    List<StockTransaction> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
    StockTransaction recordTransaction(Stock stock, StockUpdateType type, int quantityChange);
}
