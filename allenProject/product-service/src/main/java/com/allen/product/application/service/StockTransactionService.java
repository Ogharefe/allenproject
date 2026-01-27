package com.allen.product.application.service;

import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.product.application.usecase.StockTransactionUseCase;
import com.allen.product.domain.model.Stock;
import com.allen.product.domain.model.StockTransaction;
import com.allen.product.domain.model.Warehouse;
import com.allen.product.domain.port.StockTransactionRepositoryPort;
import com.allen.product.domain.port.WarehouseRepositoryPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Lazy
public class StockTransactionService implements StockTransactionUseCase {
    private final StockTransactionRepositoryPort transactionRepository;
    private final WarehouseRepositoryPort port;
    public StockTransactionService(StockTransactionRepositoryPort transactionRepository, WarehouseRepositoryPort port) {
        this.transactionRepository = transactionRepository;

        this.port = port;
    }

    @Override
    public StockTransaction recordTransaction(Stock stock, StockUpdateType type, int quantityChange) {
        Optional<Warehouse> warehouse = port.findByWarehouseId(stock.warehouseId());
        StockTransaction transaction = new StockTransaction(
                null,
                stock.productId(),
                stock.warehouseId(),
                warehouse.get().name(),
                type,
                quantityChange,
                stock.quantityOnHand(),
                LocalDateTime.now()
        );
        return transactionRepository.saveTransaction(transaction);
    }

    @Override
    public StockTransaction save(StockTransaction transaction) {
        return transactionRepository.saveTransaction(transaction);
    }

    @Override
    public List<StockTransaction> findByProductId(Long productId) {
        return transactionRepository.findByProductId(productId);
    }

    @Override
    public List<StockTransaction> findByWarehouseId(Long warehouseId) {
        return transactionRepository.findByWarehouseId(warehouseId);
    }

    @Override
    public List<StockTransaction> findByProductIdAndWarehouseId(Long productId, Long warehouseId) {
        return transactionRepository.findByProductIdAndWarehouseId(productId, warehouseId);
    }

    public List<StockTransaction> findByProductSku(String productSku) {
        return transactionRepository.findByProductSku(productSku);
    }

    public List<StockTransaction> findAll() {
        return transactionRepository.getAllTransactions();
    }
}