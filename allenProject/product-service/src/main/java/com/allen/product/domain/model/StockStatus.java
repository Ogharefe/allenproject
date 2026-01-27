package com.allen.product.domain.model;

public record StockStatus(
        Long productId,
        Long warehouseId,
        int availableQuantity,
        boolean lowStock
) {
}
