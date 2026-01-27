package com.allen.product.domain.model;

import java.time.LocalDateTime;

public record StockTransaction(
    Long transactionId,
    Long stockId,
    Long productId,
    Long warehouseId,
    String transactionType,
    int quantity,
    LocalDateTime transactionDate
) {}