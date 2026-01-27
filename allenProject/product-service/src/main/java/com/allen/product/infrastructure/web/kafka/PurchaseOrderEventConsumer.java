package com.allen.product.infrastructure.web.kafka;

import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.event_contracts.event.PurchaseOrderEvent;
import com.allen.product.application.service.StockService;
import com.allen.product.domain.model.StockUpdateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
@Component
public class PurchaseOrderEventConsumer {

    private final StockService stockService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderEventConsumer.class);

    public PurchaseOrderEventConsumer(StockService stockService) {
        this.stockService = stockService;
    }

    @Bean
    public Consumer<PurchaseOrderEvent>purchaseOrderEvents() {
        return event -> {
            logger.info("📥 Received PurchaseOrderEvent: {}", event);

            // Example: for each line in the purchase order, increase stock
            event.lines().forEach(line -> {
                StockUpdateCommand command = new StockUpdateCommand(
                        line.productId(),
                        line.warehouseId(),
                        line.quantityOrdered(),
                        StockUpdateType.PURCHASE
                );
                stockService.createOrUpdateStock(command);
            });
        };
    }
}
