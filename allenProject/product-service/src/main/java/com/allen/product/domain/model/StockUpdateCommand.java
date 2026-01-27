package com.allen.product.domain.model;

import com.allen.event_contracts.enums.StockUpdateType;

public record StockUpdateCommand(

        Long productId,
        Long warehouseId,
        int quantityChange,
       //String productSku,
        StockUpdateType updateType
) {
}
