package com.allen.purchase.applications.service;

import com.allen.event_contracts.constants.PurchaseOrderStatus;
import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.event_contracts.event.PurchaseOrderEvent;
import com.allen.event_contracts.event.PurchaseOrderLineEvent;
import com.allen.event_contracts.event.StockUpdateEvent;
import com.allen.event_contracts.event.ProductEvent;

import com.allen.purchase.applications.publisherUsecase.PurchaseOrderEventUsecase;
import com.allen.purchase.applications.usecase.PurchaseOrderUseCase;
import com.allen.purchase.domain.model.ItemSupplier;
import com.allen.purchase.domain.model.PurchaseOrder;
import com.allen.purchase.domain.model.PurchaseOrderLine;
import com.allen.purchase.domain.port.PurchaseOrderRepositoryPort;
import com.allen.purchase.domain.port.ItemSupplierRepositoryPort;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Service
    public class PurchaseOrderService implements PurchaseOrderUseCase {

        private static final Logger log = LoggerFactory.getLogger(PurchaseOrderService.class);

        private final PurchaseOrderRepositoryPort repository;
        private final PurchaseOrderEventUsecase publisher;
        private final ItemSupplierRepositoryPort supplierRepositoryPort;


        public PurchaseOrderService(PurchaseOrderRepositoryPort repository,
                                    PurchaseOrderEventUsecase publisher, @Lazy ItemSupplierRepositoryPort supplierRepositoryPort) {
            this.repository = repository;
            this.publisher = publisher;
            this.supplierRepositoryPort = supplierRepositoryPort;

        }

        public void createPurchaseOrderFromProductEvent(ProductEvent productEvent) {
            // Build a simple PurchaseOrder based on product info for low stock

            double price = productEvent.price();
            int quantityToOrder = 50; // Default quantity to order
            double totalPrice = price * quantityToOrder;
            PurchaseOrder order = new PurchaseOrder(
                    null,
                    1L, // supplierId - default
                    "AutoSupplier",
                    LocalDate.now(),
                    LocalDate.now().plusDays(7),
                    PurchaseOrderStatus.NEW,
                    List.of(new PurchaseOrderLine(
                            null,
                            productEvent.productId(),
                            productEvent.warehouseId(),
                            quantityToOrder,
                            price,
                            totalPrice
                    ))
            );

            // Reuse your existing method to persist and publish the event
            createPurchaseOrder(order);
        }

        @Override
        public PurchaseOrder createPurchaseOrder(PurchaseOrder order) {

            ItemSupplier supplier = supplierRepositoryPort.findSupplierById(order.supplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier does not exist !"));
            String supplierName = supplier.name();

            PurchaseOrder newOrder = new PurchaseOrder(
                    null,
                    order.supplierId(),
                    supplierName,
                    LocalDate.now(),
                    order.expectedDeliveryDate(),
                    PurchaseOrderStatus.NEW,
                    order.orderLines()
            );
            PurchaseOrder savedOrder = repository.create(newOrder);
            publishPurchaceOrderEvent(savedOrder);
            return savedOrder;
        }
            private void publishPurchaceOrderEvent(@NotNull PurchaseOrder order) {
            //  Convert to event
            List<PurchaseOrderLineEvent> lineEvents = order.orderLines().stream()
                    .map(line -> new PurchaseOrderLineEvent(
                            line.productId(),
                            line.warehouseId(),
                            line.quantityOrdered(),
                            line.unitPrice()
                            //StockUpdateType.PURCHASE
                    ))
                    .toList();

            PurchaseOrderEvent event = new PurchaseOrderEvent(
                    order.purchaseOrderId(),
                    order.supplierName(),
                    order.orderDate(),
                    lineEvents
            );
            publisher.publish(event);
            log.info("Published PurchaseOrderEvent: {}", event);
        }

        @Override
        public PurchaseOrder approvePurchaseOrder(Long orderId) {
            PurchaseOrder order = (PurchaseOrder) repository.findByPurchaseOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            PurchaseOrder purchaseOrder = new PurchaseOrder(
                    order.purchaseOrderId(),
                    order.supplierId(),
                    order.supplierName(),
                    order.orderDate(),
                    order.expectedDeliveryDate(),
                    PurchaseOrderStatus.APPROVED,
                    order.orderLines()
            );
            return purchaseOrder;
        }


    @Override
    public PurchaseOrder receivePurchaseOrder(Long orderId) {

        PurchaseOrder order = (PurchaseOrder) repository.findByPurchaseOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        PurchaseOrder purchaseOrder = new PurchaseOrder(
                order.purchaseOrderId(),
                order.supplierId(),
                order.supplierName(),
                order.orderDate(),
                order.expectedDeliveryDate(),
                PurchaseOrderStatus.RECEIVED,
                order.orderLines()
        );
        return repository.create(purchaseOrder);
    }

    @Override
    public void cancelPurchaseOrder(Long orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public List<PurchaseOrder> listOrders() {
        return repository.findAllPurchaseOrder();
    }
}
