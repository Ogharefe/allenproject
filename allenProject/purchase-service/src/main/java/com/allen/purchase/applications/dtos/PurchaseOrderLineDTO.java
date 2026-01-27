package com.allen.purchase.applications.dtos;

public record PurchaseOrderLineDTO(
        Long lineId,
        Long productId,
        long warehouseId,
        int quantityOrdered,
        double unitPrice,
        double totalPrice
) {
}
