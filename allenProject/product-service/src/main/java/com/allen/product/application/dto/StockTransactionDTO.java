package com.allen.product.application.dto;

import com.allen.event_contracts.enums.StockUpdateType;

import java.time.LocalDateTime;

public record StockTransactionDTO(

        Long stockTransactionId,
        Long productId,
        String productSku,
        Long warehouseId,
        String warehouseName,
        StockUpdateType type,
        Integer quantityChange,
        Integer resultingQuantity,
        LocalDateTime transactionDate
) {
}
