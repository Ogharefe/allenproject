package com.allen.purchase.infrastructure.persistence.entity;

import com.allen.event_contracts.constants.PurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "purchase_order")
@NoArgsConstructor @AllArgsConstructor
public class PurchaseOrderEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderId;
    private Long supplierId;
    @Column(name = "supplier_name")
    String supplierName;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status; // NEW, APPROVED, RECEIVED, CANCELLED
    @OneToMany(mappedBy = "purchaseOrder",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PurchaseOrderLineEntity> orderLines = new ArrayList<>();

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }

    public List<PurchaseOrderLineEntity> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<PurchaseOrderLineEntity> orderLines) {
        this.orderLines = orderLines;
    }
    public void addPurchaseOrderLine(PurchaseOrderLineEntity line) {
        if (line != null) {
            this.orderLines.add(line);
            line.setPurchaseOrder(this);
        }
}}