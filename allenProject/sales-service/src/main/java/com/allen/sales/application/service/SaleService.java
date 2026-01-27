package com.allen.sales.application.service;

import com.allen.event_contracts.enums.SaleStatus;
import com.allen.sales.application.usecase.SaleUseCase;
import com.allen.sales.domain.model.Payment;
import com.allen.sales.domain.model.ProductSnapshot;
import com.allen.sales.domain.model.Sale;
import com.allen.sales.domain.model.SaleItem;
import com.allen.sales.domain.port.SaleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService implements SaleUseCase {

    private final SaleRepositoryPort saleRepositoryPort;

    @Override
    @Transactional
    public Sale createSale(Sale sale) {
        log.info("Creating new sale");
        
        // Since Sale is a record, we must create a new instance with initialized values
        Sale saleToSave = new Sale(
                sale.idSale() != null ? sale.idSale() : UUID.randomUUID(),
                sale.date() != null ? sale.date() : LocalDateTime.now(),
                sale.customerId(),
                sale.items() != null ? sale.items() : new ArrayList<>(),
                sale.totalAmount() != null ? sale.totalAmount() : BigDecimal.ZERO,
                sale.status() != null ? sale.status() : SaleStatus.PENDING_PAYMENT,
                sale.payment()
        );
        
        return saleRepositoryPort.save(saleToSave);
    }

    @Override
    @Transactional
    public Sale addItem(UUID saleId, ProductSnapshot product, int quantity) {
        log.info("Adding item to sale: {}, product: {}", saleId, product.productId());
        Sale sale = getSale(saleId);

        validateSaleIsModifiable(sale);

        // Usage of the domain method which returns a new Sale instance
        Sale updatedSale = sale.addItem(product, quantity);
        
        return saleRepositoryPort.save(updatedSale);
    }

    @Override
    @Transactional
    public Sale removeItem(UUID saleId, UUID productId) {
        log.info("Removing item: {} from sale: {}", productId, saleId);
        Sale sale = getSale(saleId);

        validateSaleIsModifiable(sale);

        // Manually reconstruct Sale since no removeItem helper exists in the Record
        List<SaleItem> newItems = sale.items().stream()
                .filter(item -> !item.productId().equals(productId))
                .collect(Collectors.toList());

        BigDecimal newTotal = calculateTotal(newItems);

        Sale updatedSale = new Sale(
                sale.idSale(),
                sale.date(),
                sale.customerId(),
                newItems,
                newTotal,
                sale.status(),
                sale.payment()
        );

        return saleRepositoryPort.save(updatedSale);
    }

    @Override
    @Transactional
    public Sale updateItemQuantity(UUID saleId, UUID productId, int quantity) {
        log.info("Updating item quantity: {} for sale: {}", quantity, saleId);
        Sale sale = getSale(saleId);

        validateSaleIsModifiable(sale);

        // Manually reconstruct items list with updated quantity
        List<SaleItem> newItems = sale.items().stream()
                .map(item -> {
                    if (item.productId().equals(productId)) {
                        return new SaleItem(
                                UUID.randomUUID(),
                                item.productId(),
                                item.productName(),
                                item.unitPrice(),
                                quantity
                        );
                    }
                    return item;
                })
                .collect(Collectors.toList());

        BigDecimal newTotal = calculateTotal(newItems);

        Sale updatedSale = new Sale(
                sale.idSale(),
                sale.date(),
                sale.customerId(),
                newItems,
                newTotal,
                sale.status(),
                sale.payment()
        );

        return saleRepositoryPort.save(updatedSale);
    }

    @Override
    @Transactional
    public Sale completeSale(UUID saleId, Payment payment) {
        log.info("Completing sale: {}", saleId);
        Sale sale = getSale(saleId);

        if (sale.status() != SaleStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Sale is not in PENDING state");
        }

        // Usage of domain method
        Sale completedSale = sale.completeSale(payment);
        
        return saleRepositoryPort.save(completedSale);
    }

    @Override
    @Transactional
    public Sale cancelSale(UUID saleId) {
        log.info("Cancelling sale: {}", saleId);
        Sale sale = getSale(saleId);

        if (sale.status() == SaleStatus.COMPLETED) {
             throw new IllegalStateException("Cannot cancel a completed order");
        }

        // Usage of domain method
        Sale cancelledSale = sale.cancelSale();
        
        return saleRepositoryPort.save(cancelledSale);
    }

    @Override
    public Sale getSale(UUID saleId) {
        return saleRepositoryPort.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found with id: " + saleId));
    }

    @Override
    public void deleteSale(UUID saleId) {

        log.info("Deleting sale: {}", saleId);

        if (!saleRepositoryPort.existsById(saleId)) {
            throw new IllegalArgumentException("Sale not found with id: " + saleId);
        }

        saleRepositoryPort.deleteSaleById(saleId);
    }


    private void validateSaleIsModifiable(Sale sale) {
        if (sale.status() != SaleStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Cannot modify a sale that is not PENDING. Current status: " + sale.status());
        }
    }

    private BigDecimal calculateTotal(List<SaleItem> items) {
        return items.stream()
                .map(SaleItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
