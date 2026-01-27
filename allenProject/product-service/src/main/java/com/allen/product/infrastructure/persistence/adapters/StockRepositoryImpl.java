package com.allen.product.infrastructure.persistence.adapters;

import com.allen.product.domain.model.Stock;
import com.allen.product.domain.port.StockRepositoryPort;
import com.allen.product.infrastructure.persistence.entity.StockEntity;
import com.allen.product.infrastructure.persistence.mapper.StockMapper;
import com.allen.product.infrastructure.persistence.springdataJpaRepository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public class StockRepositoryImpl implements StockRepositoryPort {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private static final Logger logger = LoggerFactory.getLogger(StockRepositoryImpl.class);

    public StockRepositoryImpl(@Lazy StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    @Override
    @Transactional
    public Stock saveStock(Stock stock) {

        StockEntity stockEntity = stockMapper.StockToStockEntity(stock);
        return stockMapper.stockEntityToStock(stockRepository.save(stockEntity));
    }

    @Override
    public Optional<Stock> findByStockId(Long stockId) {

        return stockRepository.findById(stockId)
                .map(stockMapper::stockEntityToStock);
         }

    @Override
    @Transactional(readOnly = true)
    public Optional<Stock> findByProductAndWarehouse(Long productId, Long warehouseId) {
        try {
            return stockRepository.findByProduct_ProductIdAndWarehouse_WarehouseId(productId, warehouseId)
                    .map(stockMapper::stockEntityToStock);
        } catch (Exception e) {
            logger.warn("Error in findByProductIdAndWarehouseId: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(stockMapper::stockEntityToStock)
                .toList();
    }

    @Override
    public void deleteById(Long stockId) {

        stockRepository.deleteById(stockId);
    }

}


