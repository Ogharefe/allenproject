package com.allen.product.domain.model;

import java.time.LocalDate;

public record Stock(
        Long stockId,
        Long productId,
        Long warehouseId,
        String warehouseName,
        Integer quantityOnHand,
        Integer quantityReserved,
        LocalDate lastUpdated,
        int quantityChange) {

    public int getAvailableQuantity() {
        return quantityOnHand - quantityReserved;
    }
    public boolean isLowStock(int threshold) {
        return getAvailableQuantity() <= threshold;
    }
}






