package com.allen.purchase.applications.publisherUsecase;

import com.allen.event_contracts.event.PurchaseOrderEvent;

public interface PurchaseOrderEventUsecase {

    void publish(PurchaseOrderEvent event);
}
