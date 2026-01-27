package com.allen.purchase.domain.model;

import com.allen.event_contracts.constants.PurchaseOrderStatus;
import com.allen.event_contracts.enums.StockUpdateType;

import java.time.LocalDate;
import java.util.List;

public record PurchaseOrder(
        Long purchaseOrderId,
        Long supplierId,
        String supplierName,
        LocalDate orderDate,
        LocalDate expectedDeliveryDate,
        PurchaseOrderStatus status,
        List<PurchaseOrderLine> orderLines

){
}