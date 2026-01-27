package com.allen.product.domain.model;

import com.allen.event_contracts.enums.StockUpdateType;

import java.time.LocalDateTime;

public record StockTransaction(
         Long stockTransactionId,
         Long productId,
         Long warehouseId,
         String warehouseName,
         StockUpdateType type,
         Integer quantityChange,
         Integer resultingQuantity,
         LocalDateTime transactionDate
) {
}
