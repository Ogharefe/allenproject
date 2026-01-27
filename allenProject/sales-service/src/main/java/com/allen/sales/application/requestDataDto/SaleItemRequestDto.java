package com.allen.sales.application.requestDataDto;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItemRequestDto(
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        String sku,
        int quantity,
        String description
) {
    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}