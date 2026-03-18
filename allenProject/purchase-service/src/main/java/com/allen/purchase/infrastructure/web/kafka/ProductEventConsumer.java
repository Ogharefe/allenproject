package com.allen.purchase.infrastructure.web.kafka;


import com.allen.event_contracts.event.ProductEvent;
import com.allen.purchase.applications.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.function.Consumer;

@Service
public class ProductEventConsumer {

    private final PurchaseOrderService service;
    private static final Logger log = LoggerFactory.getLogger(ProductEventConsumer.class);


    public ProductEventConsumer(PurchaseOrderService service) {
        this.service = service;
    }

    // Spring Cloud Stream will auto-detect this as a Consumer<ProductEvent>
    @Bean
    public Consumer<ProductEvent> productEventsConsumer() {
        return event -> {
            if (event.type() == com.allen.event_contracts.enums.ProductEventType.LOW_STOCK) {
                log.info("Received ProductEvent LOW_STOCK: " + event);
                service.createPurchaseOrderFromProductEvent(event);
            }
        };
    }

}
