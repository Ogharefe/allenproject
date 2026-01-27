package com.allen.purchase.domain.model;

public record PurchaseOrderLine (
        Long lineId,
        Long productId,
        long warehouseId,
        int quantityOrdered,
        double unitPrice,
        double totalPrice // derived = quantity * unitPrice
){
}
