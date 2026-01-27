package com.allen.sales.infrastructure.persistence.entity;

import com.allen.event_contracts.enums.SaleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sales_orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID idSale;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name ="customer_id")
    private UUID customerId;

    @Column(name ="total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SaleStatus status;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PaymentEntity payment;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SaleItemEntity> items = new ArrayList<>();

    // Helper method to manage bi-directional relationship
    public void addItem(SaleItemEntity item) {
        items.add(item);
        item.setSale(this);
    }

    public void removeItem(SaleItemEntity item) {
        items.remove(item);
        item.setSale(null);
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
        if (payment != null) {
            payment.setSale(this);
        }
    }

    public UUID getIdSale() {
        return idSale;
    }

    public void setIdSale(UUID idSale) {
        this.idSale = idSale;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public List<SaleItemEntity> getItems() {
        return items;
    }

    public void setItems(List<SaleItemEntity> items) {
        this.items = items;
    }
}