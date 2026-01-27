package com.allen.sales.infrastructure.web.kafka;

import com.allen.event_contracts.enums.ProductEventType;
import com.allen.event_contracts.event.ProductEvent;
import com.allen.sales.application.usecase.ProductSnapshotUseCase;
import com.allen.sales.domain.model.ProductSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class ProductCreatedConsumerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ProductCreatedConsumerConfiguration.class);

    private final ProductSnapshotUseCase productSnapshotUseCase;

    public ProductCreatedConsumerConfiguration(ProductSnapshotUseCase productSnapshotUseCase) {
        this.productSnapshotUseCase = productSnapshotUseCase;
    }

    @Bean
    public Consumer<ProductEvent> productCreatedConsumer() {
        return event -> {
            if (event == null) {
                return;
            }

            if (event.type() != ProductEventType.CREATED && event.type() != ProductEventType.UPDATED) {
                log.debug("Ignoring ProductEvent type={} sku={}", event.type(), event.sku());
                return;
            }

            UUID productId;
            try {
                productId = UUID.fromString(String.valueOf(event.productId()));
            } catch (Exception ex) {
                log.warn("Cannot map productId={} to UUID. Event ignored: {}", event.productId(), event);
                return;
            }

            ProductSnapshot snapshot = new ProductSnapshot(
                    productId,
                    event.name(),
                    event.description(),
                    BigDecimal.valueOf(event.price()),
                    event.sku()
            );

            productSnapshotUseCase.upsert(snapshot);
            log.info("Upserted ProductSnapshot for sku={} productId={}", snapshot.sku(), snapshot.productId());
        };
    }
}
