package com.allen.product.application.service;

import com.allen.product.application.constants.StockUpdateType;
import com.allen.product.domain.model.Stock;
import com.allen.product.domain.model.StockTransaction;
import com.allen.product.domain.port.StockTransactionRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockTransactionService {
    private final StockTransactionRepositoryPort transactionRepository;

    public StockTransactionService(StockTransactionRepositoryPort transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public StockTransaction recordTransaction(Stock stock, StockUpdateType type, int quantityChange) {
        StockTransaction transaction = new StockTransaction(
            null,
            stock.productId(),
            stock.productSku(),
            stock.warehouseId(),
            stock.warehouseName(),
            type,
            quantityChange,
            stock.quantityOnHand(),
            LocalDateTime.now()
        );
        return transactionRepository.save(transaction);
    }

    public List<StockTransaction> findByProductId(Long productId) {
        return transactionRepository.findByProductId(productId);
    }

    public List<StockTransaction> findByWarehouseId(Long warehouseId) {
        return transactionRepository.findByWarehouseId(warehouseId);
    }

    public List<StockTransaction> findByProductIdAndWarehouseId(Long productId, Long warehouseId) {
        return transactionRepository.findByProductIdAndWarehouseId(productId, warehouseId);
    }

    public List<StockTransaction> findByProductSku(String productSku) {
        return transactionRepository.findByProductSku(productSku);
    }

    public List<StockTransaction> findAll() {
        return transactionRepository.findAll();
    }
}