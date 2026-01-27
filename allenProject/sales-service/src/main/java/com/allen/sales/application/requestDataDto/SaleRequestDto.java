package com.allen.sales.application.requestDataDto;

import com.allen.event_contracts.enums.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SaleRequestDto(
        UUID idSale,
        LocalDateTime date,
        UUID customerId,
        BigDecimal totalAmount,
        SaleStatus status,
        List<SaleItemRequestDto> items
) {
}