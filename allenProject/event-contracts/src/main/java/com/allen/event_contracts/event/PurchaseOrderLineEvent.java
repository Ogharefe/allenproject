package com.allen.event_contracts.event;

import com.allen.event_contracts.enums.StockUpdateType;

public record PurchaseOrderLineEvent(

        Long productId,
        Long warehouseId,
        int quantityOrdered,
        double unitPrice
       // StockUpdateType stockUpdateType
) {
}
