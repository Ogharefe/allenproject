package com.allen.sales.application.requestDataDto;


import com.allen.event_contracts.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentRequesttDto(
        PaymentMethod method,
        BigDecimal amount,
        LocalDateTime paymentDate
) {
}