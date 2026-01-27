package com.allen.purchase.domain.port;

import com.allen.purchase.domain.model.PurchaseOrder;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepositoryPort {
    PurchaseOrder create(PurchaseOrder order);
    Optional<PurchaseOrder> findByPurchaseOrderId(Long id);
    List<PurchaseOrder> findAllPurchaseOrder();
    void deleteById(Long id);
}