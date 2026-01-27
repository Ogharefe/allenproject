package com.allen.sales.domain.model;

import com.allen.event_contracts.enums.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record Sale(
        UUID idSale,
        LocalDateTime date,
        UUID customerId,
        List<SaleItem> items,
        BigDecimal totalAmount,
        SaleStatus status,
        Payment payment
) {

    // Add a new item: returns a new Sale instance
    public Sale addItem(ProductSnapshot product, int quantity) {
        List<SaleItem> newItems = new ArrayList<>(this.items);
        newItems.add(new SaleItem(
                UUID.randomUUID(),
                product.productId(),
                product.name(),
                product.unitPrice(),
                quantity
        ));
        BigDecimal newTotal = calculateTotal(newItems);
        return new Sale(this.idSale, this.date, this.customerId, newItems, newTotal, this.status, this.payment);
    }

    // Apply discount: returns a new Sale instance
    public Sale applyDiscount(BigDecimal discountAmount) {
        BigDecimal discountedTotal = totalAmount.subtract(discountAmount);
        return new Sale(this.idSale, this.date, this.customerId, this.items, discountedTotal, this.status, this.payment);
    }

    // Complete sale: returns a new Sale instance with updated status and payment
    public Sale completeSale(Payment payment) {
        return new Sale(this.idSale, this.date, this.customerId, this.items, this.totalAmount, SaleStatus.COMPLETED, payment);
    }

    // Cancel sale: returns a new Sale instance with updated status
    public Sale cancelSale() {
        return new Sale(this.idSale, this.date, this.customerId, this.items, this.totalAmount, SaleStatus.CANCELLED, this.payment);
    }

    // Calculate total from items
    private BigDecimal calculateTotal(List<SaleItem> items) {
        return items.stream()
                .map(SaleItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
