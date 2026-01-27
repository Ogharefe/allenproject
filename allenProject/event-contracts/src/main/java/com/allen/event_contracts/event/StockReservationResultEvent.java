package com.allen.event_contracts.event;

import java.util.List;
import java.util.UUID;

public record StockReservationResultEvent(
        UUID commandId,
        UUID saleId,
        ReservationStatus status,
        List<LineResult> lineResults,
        String message
) {
    public enum ReservationStatus { SUCCEEDED, FAILED }

    public record LineResult(
            Long productId,
            Integer quantity,
            Long warehouseId, // populated on success (chosen warehouse)
            boolean success,
            String reason
    ) {}
}
