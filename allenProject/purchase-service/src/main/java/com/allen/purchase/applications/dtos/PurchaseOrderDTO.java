package com.allen.purchase.applications.dtos;

import com.allen.event_contracts.constants.PurchaseOrderStatus;
import com.allen.purchase.domain.model.PurchaseOrderLine;

import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderDTO(
        Long purchaseOrderId,
        Long supplierId,
        String supplierName,
        LocalDate orderDate,
        LocalDate expectedDeliveryDate,
        PurchaseOrderStatus status,
        List<PurchaseOrderLineDTO> orderLines
) {
}
