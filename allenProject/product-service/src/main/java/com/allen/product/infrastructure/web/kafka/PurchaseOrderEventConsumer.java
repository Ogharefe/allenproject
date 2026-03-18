package com.allen.product.infrastructure.web.kafka;

import com.allen.event_contracts.enums.ProductEventType;
import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.event_contracts.event.ProductEvent;
import com.allen.event_contracts.event.PurchaseOrderEvent;
import com.allen.event_contracts.event.StockUpdateCommand;
import com.allen.event_contracts.event.StockUpdateEvent;
import com.allen.product.application.service.StockService;
import com.allen.product.domain.model.Stock;
import com.allen.product.domain.port.ProductRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class PurchaseOrderEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderEventConsumer.class);
    private final ProductRepositoryPort productRepositoryPort;
    private final StockService stockService;
    private final StreamBridge streamBridge;

    public PurchaseOrderEventConsumer(ProductRepositoryPort productRepositoryPort, StockService stockService, StreamBridge streamBridge) {
        this.productRepositoryPort = productRepositoryPort;
        this.stockService = stockService;
        this.streamBridge = streamBridge;
    }


    @Bean
    public Consumer<PurchaseOrderEvent> purchaseOrderEvents() {
        return event -> {
            logger.info("📥 Received PurchaseOrderEvent: {}", event);

            // For each line in the purchase order, update stock directly
            event.lines().forEach(line -> {
                StockUpdateCommand command = new StockUpdateCommand(
                        line.productId(),
                        line.warehouseId(),
                        line.quantityOrdered(),
                        StockUpdateType.PURCHASE
                );
                Stock updatedStock = stockService.createOrUpdateStock(command);

                // Publish StockUpdateEvent to stock-events topic
                var product = productRepositoryPort.findByProductId(line.productId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                StockUpdateEvent stockEvent = new StockUpdateEvent(
                        updatedStock.stockId(),
                        updatedStock.productId(),
                        product.price(),
                        updatedStock.warehouseId(),
                        updatedStock.warehouseName(),
                        updatedStock.quantityOnHand(),
                        updatedStock.quantityReserved(),
                        updatedStock.lastUpdated(),
                        updatedStock.quantityChange()
                );
                streamBridge.send("stock-events", stockEvent);

                // Check if low stock and publish ProductEvent
                int lowStockThreshold = 10;
                if (updatedStock.quantityOnHand() <= lowStockThreshold) {
                    ProductEvent productEvent = new ProductEvent(
                            product.productId(),
                            product.sku(),
                            product.name(),
                            product.description(),
                            product.price(),
                            ProductEventType.LOW_STOCK,
                            updatedStock.warehouseId()
                    );
                    streamBridge.send("productUpdateEvents-out-0", productEvent);
                }
            });
        };
    }
}
