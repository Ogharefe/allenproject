package com.allen.sales.domain.model;

import com.allen.sales.infrastructure.persistence.entity.SaleEntity;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItem(
        UUID idSaleItem,
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        int quantity

) {
    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
