package com.allen.product.application.dto;


import com.allen.event_contracts.enums.StockUpdateType;

public record StockUpdateCommandDTO(
        Long productId,
        Long warehouseId,
        int quantityChange,
        StockUpdateType updateType
) {
}
