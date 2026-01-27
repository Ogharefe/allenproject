package com.allen.event_contracts.event;

import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderEvent(
        Long purchaseOrderId,
        String supplierName,
        LocalDate orderDate,
        List<PurchaseOrderLineEvent> lines

) {}
