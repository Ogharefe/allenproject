package com.allen.product.domain.port;

import com.allen.product.domain.model.Stock;
import java.util.List;
import java.util.Optional;

public interface StockRepositoryPort {

    Stock saveStock(Stock stock);
    Optional<Stock> findByStockId(Long stockId);
    Optional<Stock> findByProductAndWarehouse(Long productId, Long warehouseId);
    List<Stock> getAllStocks();
    void deleteById(Long stockId);
}
