package com.allen.purchase.applications.service;

import com.allen.purchase.applications.usecase.PurchaseOrderLineUsecase;
import com.allen.purchase.domain.model.PurchaseOrderLine;
import com.allen.purchase.domain.port.PurchaseOrderLineReposPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderLineService implements PurchaseOrderLineUsecase {

    private final PurchaseOrderLineReposPort port;

    public PurchaseOrderLineService(PurchaseOrderLineReposPort port) {
        this.port = port;
    }

    @Override
    public PurchaseOrderLine create(PurchaseOrderLine purchaseOrderLine) {
        return port.create(purchaseOrderLine);
    }

    @Override
    public List<PurchaseOrderLine> getAll() {
        return port.findAll();
    }

    @Override
    public Optional<PurchaseOrderLine> findByPurchaseOrderLineId(final Long id) {
        Optional<PurchaseOrderLine> line = port.findByIdPurchaseOrderLine(id);
        return line;
    }

    @Override
    public void deletPurchaseOrderline(Long id) {

        Optional<PurchaseOrderLine> existingOrderLine = findByPurchaseOrderLineId(id);
        if (existingOrderLine != null) {
            port.deletPurchaseOrderLine(id);
        }
    }
    @Override
    public PurchaseOrderLine update(PurchaseOrderLine line, Long id) {

        Optional<PurchaseOrderLine> existingOrderLine = findByPurchaseOrderLineId(id);
        PurchaseOrderLine updatedOrderLine = new PurchaseOrderLine(
                id,
                line.productId(),
                line.warehouseId(),
                line.quantityOrdered(),
                line.unitPrice(),
                calculateTotal(line)
        );
        return port.update(updatedOrderLine,id);
    }

    private double calculateTotal(PurchaseOrderLine line) {
        return line.quantityOrdered() * line.unitPrice();
    }

}
