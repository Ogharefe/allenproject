package com.allen.event_contracts.event;

import com.allen.event_contracts.enums.StockUpdateType;

import java.util.List;
import java.util.UUID;

public record StockUpdateBatchCommandEvent(
        UUID commandId,
        UUID saleId,
        StockUpdateType updateType,
        List<Line> lines
) {
    public record Line(
            Long productId,
            Integer quantityChange,
            Long warehouseId // nullable for RESERVATION; required for SALE (commit) and RELEASE (if you add it)
    ) {}
}
