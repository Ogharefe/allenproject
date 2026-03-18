package com.allen.event_contracts.event;


import com.allen.event_contracts.enums.ProductEventType;

public record ProductEvent(
        Long productId,
        String sku,
        String name,
        String description,
        double price,
        ProductEventType type,
        Long warehouseId
) {}
