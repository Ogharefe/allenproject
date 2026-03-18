package com.allen.product.application.usecase;


import com.allen.event_contracts.event.StockUpdateCommand;
import com.allen.product.domain.model.Stock;
import com.allen.product.domain.model.StockStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockUseCase {

    Stock createOrUpdateStock(StockUpdateCommand command);
    StockStatus trackStock(Long productId, Long warehouseId, int lowStockThreshold);
    void deletStock(Long stockId);
    List<Stock> getStockList();
    Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
    Optional<Stock> findById(Long stockId);
    Stock reserveStock(int quantity, Long idStock);
    void releaseStock(UUID productId, int quantity);

}
