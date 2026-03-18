package com.allen.product.application.service;

import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.event_contracts.event.StockUpdateCommand;
import com.allen.product.application.usecase.StockTransactionUseCase;
import com.allen.product.application.usecase.StockUseCase;
import com.allen.product.domain.model.*;
import com.allen.product.domain.port.ProductRepositoryPort;
import com.allen.product.domain.port.StockRepositoryPort;
import com.allen.product.domain.port.WarehouseRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class StockService implements StockUseCase {
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);
    private final StockTransactionUseCase transactionService;
    private final StockRepositoryPort stockRepositoryPort;
    private final WarehouseRepositoryPort warehouseRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;
    private final StreamBridge streamBridge;

    public StockService(StockTransactionUseCase transactionService,
                        StockRepositoryPort stockRepositoryPort,
                        WarehouseRepositoryPort warehouseRepositoryPort, ProductRepositoryPort productRepositoryPort, StreamBridge streamBridge) {
        this.transactionService = transactionService;
        this.stockRepositoryPort = stockRepositoryPort;
        this.warehouseRepositoryPort = warehouseRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
        this.streamBridge = streamBridge;
    }

    @Override
    public Stock createOrUpdateStock(StockUpdateCommand command) {
        Stock updatedStock = stockRepositoryPort.findByProductAndWarehouse(command.productId(), command.warehouseId())
                .map(existingStock -> updateExistingStock(existingStock, command))
                .orElseGet(() -> createNewStock(command));

        // Record the transaction
        transactionService.recordTransaction(updatedStock, command.updateType(), command.quantityChange());

        return updatedStock;
    }

    private Stock updateExistingStock(Stock existingStock, StockUpdateCommand command) {
        int currentQuantity = existingStock.quantityOnHand();

        int newQuantity = calculateNewQuantity(
                currentQuantity,
                command.quantityChange(),
                command.updateType()
        );

        Stock newStock = new Stock(
                existingStock.stockId(),
                existingStock.productId(),
                existingStock.warehouseId(),
                existingStock.warehouseName(),
                newQuantity,
                existingStock.quantityReserved(),
                LocalDate.now(),
                command.quantityChange()
        );
        return stockRepositoryPort.saveStock(newStock);
    }

    private Stock createNewStock(StockUpdateCommand command) {
        var warehouse = warehouseRepositoryPort.findByWarehouseId(command.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + command.warehouseId()));

        int newQuantity = calculateNewQuantity(0, command.quantityChange(), command.updateType());

        Stock newStock = new Stock(
                null,
                command.productId(),
                warehouse.warehouseId(),
                warehouse.name(),
                newQuantity,
                0,
                LocalDate.now(),
                command.quantityChange()
        );

        return stockRepositoryPort.saveStock(newStock);
    }

    @Override
    public StockStatus trackStock(Long productId, Long warehouseId, int lowStockThreshold) {

        Stock stock = findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        int availableQty = stock.getAvailableQuantity();
        boolean isLowStock = stock.isLowStock(lowStockThreshold);
        if (isLowStock) {
            logger.warn("Low stock detected for product {} in warehouse {}: available={}",
                    productId, warehouseId, availableQty);
        }
        return new StockStatus(stock.productId(), stock.warehouseId(), availableQty, isLowStock);
    }


    private void validateStockInputs(Stock stock, Long warehouseId) {
        Objects.requireNonNull(stock, "Stock cannot be null");
        Objects.requireNonNull(stock.productId(), "Product ID is required");
        Objects.requireNonNull(warehouseId, "Warehouse ID is required");
    }

    @Override
    public void deletStock(Long stockId) {
      stockRepositoryPort.deleteById(stockId);

    }

    @Override
    public List<Stock> getStockList() {
        return stockRepositoryPort.getAllStocks();
    }

    @Override
    public Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId) {
        return stockRepositoryPort.findByProductAndWarehouse(productId, warehouseId);
    }

    @Override
    public Optional<Stock> findById(Long stockId) {
        return stockRepositoryPort.findByStockId(stockId);
    }

    @Override
    public Stock reserveStock(int quantityToReserve, Long idStock) {

        Stock stock = findById(idStock).orElseThrow(() -> new RuntimeException("Stock non trouvé !"));
        int available = stock.quantityOnHand() - stock.quantityReserved();
        if (quantityToReserve > available) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        // Calculate new reserved quantity
        int newReserved = stock.quantityReserved() + quantityToReserve;
        if (newReserved < 0) {
            throw new IllegalArgumentException("Reserved quantity cannot be negative");
        }

        // Deduct only the new reservation quantity
        int newQuantityOnHand = calculateNewQuantity(
                stock.quantityOnHand(),
                quantityToReserve,
                StockUpdateType.RESERVATION
        );

        Stock updated = new Stock(
                stock.stockId(),
                stock.productId(),
                stock.warehouseId(),
                stock.warehouseName(),
                newQuantityOnHand,
                newReserved,
                LocalDate.now(),
                quantityToReserve
        );

        stockRepositoryPort.saveStock(updated);
        return updated;
    }

    @Override
    public void releaseStock(UUID productId, int quantity) {

    }

    private int calculateNewQuantity(int currentQuantity, int quantityChange, StockUpdateType updateType) {
        int newQuantity;

        switch (updateType) {
            case PURCHASE:
                if (quantityChange < 0) {
                    throw new IllegalArgumentException("PURCHASE must use a non-negative quantityChange. Received: " + quantityChange);
                }
                newQuantity = currentQuantity + quantityChange;
                break;
            case SALE:
            case RESERVATION:
                if (quantityChange < 0) {
                    throw new IllegalArgumentException(updateType + " must use a non-negative quantityChange. Received: " + quantityChange);
                }
                newQuantity = currentQuantity - quantityChange;
                if (newQuantity < 0) {
                    throw new IllegalArgumentException(
                            "Insufficient stock quantity for " + updateType +
                                    ". Current: " + currentQuantity +
                                    ", requested: " + quantityChange
                    );
                }
                break;
            case RELEASE_RESERVATION:
                if (quantityChange < 0) {
                    throw new IllegalArgumentException("RELEASE_RESERVATION must use a non-negative quantityChange. Received: " + quantityChange);
                }
                newQuantity = currentQuantity + quantityChange;
                break;
            default:
                throw new IllegalArgumentException("Unknown StockUpdateType: " + updateType);
        }
        return newQuantity;
    }
}
