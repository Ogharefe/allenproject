package com.allen.sales.domain.model;

import com.allen.event_contracts.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Payment(
        PaymentMethod method,
        BigDecimal amount,
        LocalDateTime paymentDate
) {
}
