package com.allen.product.infrastructure.persistence.adapters;

import com.allen.product.domain.model.StockTransaction;
import com.allen.product.domain.port.StockTransactionRepositoryPort;
import com.allen.product.infrastructure.persistence.mapper.StockTransactionMapper;
import com.allen.product.infrastructure.persistence.springdataJpaRepository.ProductRepository;
import com.allen.product.infrastructure.persistence.springdataJpaRepository.StockTransactionRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
@Lazy
public class StockTransactionRepositoryImpl implements StockTransactionRepositoryPort {
    private final StockTransactionRepository repository;
    private final StockTransactionMapper mapper;
    private final ProductRepository productRepository;

    public StockTransactionRepositoryImpl(@Lazy StockTransactionRepository repository, StockTransactionMapper mapper, ProductRepository productRepository) {
        this.repository = repository;
        this.mapper = mapper;

        this.productRepository = productRepository;
    }

    @Override
    public StockTransaction saveTransaction(StockTransaction stockTransaction) {

        var entity = mapper.stockTransactionToEntity(stockTransaction);
        var savedEntity = repository.save(entity);
        return mapper.entityToStockTransaction(savedEntity);
    }

    @Override
    public List<StockTransaction> findByProductId(Long productId) {
        var entities = repository.findByProductId(productId);
        return mapper.listEntityToStockTransactionList(entities);
    }

    @Override
    public List<StockTransaction> findByWarehouseId(Long warehouseId) {
        var entities = repository.findByWarehouseId(warehouseId);
        return mapper.listEntityToStockTransactionList(entities);
    }
    @Override
    @Transactional(readOnly = true)
    public List<StockTransaction> findByProductIdAndWarehouseId(Long productId, Long warehouseId) {
        try {
            var entities = repository.findByProductIdAndWarehouseId(productId, warehouseId);
            return mapper.listEntityToStockTransactionList(entities);
        } catch (Exception e) {
            System.err.println("Error in findByProductIdAndWarehouseId: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<StockTransaction> findByProductSku(String productSku) {
        var entities = repository.findByProductSku(productSku);
        return mapper.listEntityToStockTransactionList(entities);
    }

    @Override
    public List<StockTransaction> getAllTransactions() {
        var entities = repository.findAll();
        return mapper.listEntityToStockTransactionList(entities);
    }

}