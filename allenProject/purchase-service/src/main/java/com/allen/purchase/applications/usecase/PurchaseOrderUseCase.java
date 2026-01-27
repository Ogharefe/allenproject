package com.allen.purchase.applications.usecase;

import com.allen.purchase.domain.model.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderUseCase {
    PurchaseOrder createPurchaseOrder(PurchaseOrder order);
    PurchaseOrder approvePurchaseOrder(Long orderId);
    PurchaseOrder receivePurchaseOrder(Long orderId);
    void cancelPurchaseOrder(Long orderId);
    List<PurchaseOrder> listOrders();
}
