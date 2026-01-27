package com.allen.purchase.applications.usecase;

import com.allen.purchase.domain.model.PurchaseOrderLine;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderLineUsecase {

    PurchaseOrderLine create(PurchaseOrderLine purchaseOrderLine);
    List<PurchaseOrderLine> getAll();
    Optional<PurchaseOrderLine> findByPurchaseOrderLineId(Long id);
    void deletPurchaseOrderline(Long id);
    PurchaseOrderLine update(PurchaseOrderLine line, Long id);
}
