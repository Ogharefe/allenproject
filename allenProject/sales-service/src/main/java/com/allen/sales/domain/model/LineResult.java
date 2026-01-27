package com.allen.sales.domain.model;

public record LineResult(
        Long productId,
        Integer quantity,
        Long warehouseId, // filled on success
        boolean success,
        String reason      // filled on failure
) {}