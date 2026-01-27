package com.allen.event_contracts.event;

import com.allen.event_contracts.enums.StockUpdateType;

public record StockUpdateCommandEvent(
        Long productId,
        Long warehouseId,
        int quantityChange,
        //String productSku,
        StockUpdateType updateType
) {
}
