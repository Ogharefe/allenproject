package com.allen.purchase.domain.port;

import com.allen.purchase.domain.model.PurchaseOrderLine;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderLineReposPort {

    PurchaseOrderLine create(PurchaseOrderLine orderLine);
    PurchaseOrderLine update(PurchaseOrderLine orderLine, Long id);
    List<PurchaseOrderLine> findAll();
    Optional<PurchaseOrderLine> findByIdPurchaseOrderLine(Long id);
    void deletPurchaseOrderLine(Long id);

}
