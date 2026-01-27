package com.allen.event_contracts.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StockUpdateEvent(
        Long stockId,
        Long productId,
        double price,
        Long warehouseId,
        String warehouseName,
        Integer quantityOnHand,
        Integer quantityReserved,
        LocalDate lastUpdated,
        int quantityChange
) {}
