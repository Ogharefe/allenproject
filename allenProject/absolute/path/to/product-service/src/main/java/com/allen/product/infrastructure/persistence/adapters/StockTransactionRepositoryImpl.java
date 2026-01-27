package com.allen.product.infrastructure.persistence.adapters;

import com.allen.product.domain.model.StockTransaction;
import com.allen.product.domain.port.StockTransactionRepositoryPort;
import com.allen.product.infrastructure.persistence.mapper.StockTransactionMapper;
import com.allen.product.infrastructure.persistence.springdataJpaRepository.StockTransactionRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StockTransactionRepositoryImpl implements StockTransactionRepositoryPort {
    private final StockTransactionRepository repository;
    private final StockTransactionMapper mapper;

    public StockTransactionRepositoryImpl(StockTransactionRepository repository, StockTransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public StockTransaction save(StockTransaction stockTransaction) {
        var entity = mapper.toEntity(stockTransaction);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<StockTransaction> findByProductId(Long productId) {
        var entities = repository.findByProductId(productId);
        return mapper.toDomainList(entities);
    }

    @Override
    public List<StockTransaction> findByWarehouseId(Long warehouseId) {
        var entities = repository.findByWarehouseId(warehouseId);
        return mapper.toDomainList(entities);
    }

    @Override
    public List<StockTransaction> findByProductIdAndWarehouseId(Long productId, Long warehouseId) {
        var entities = repository.findByProductIdAndWarehouseId(productId, warehouseId);
        return mapper.toDomainList(entities);
    }

    @Override
    public List<StockTransaction> findByProductSku(String productSku) {
        var entities = repository.findByProductSku(productSku);
        return mapper.toDomainList(entities);
    }

    @Override
    public List<StockTransaction> findAll() {
        var entities = repository.findAll();
        return mapper.toDomainList(entities);
    }
}