package com.allen.sales.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductSnapshot(

        UUID productId,
        String name,
        String description,
        BigDecimal unitPrice,
        String sku
) {
}
