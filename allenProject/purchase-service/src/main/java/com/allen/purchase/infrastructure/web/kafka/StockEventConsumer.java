package com.allen.purchase.infrastructure.web.kafka;


import com.allen.event_contracts.event.StockUpdateEvent;
import com.allen.purchase.applications.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Service
public class StockEventConsumer {

    private final PurchaseOrderService service;
    private static final Logger log = LoggerFactory.getLogger(StockEventConsumer.class);


    public StockEventConsumer(PurchaseOrderService service) {
        this.service = service;
    }

    // Spring Cloud Stream will auto-detect this as a Consumer<ProductEvent>
    @Bean
    public Consumer<StockUpdateEvent>stockUpdateEventsConsumer() {
        return event -> {
         log.info("Received StockEvent: " + event);;
            service.createPurchaseOrderFromStockEvent(event);
        };
    }

}
