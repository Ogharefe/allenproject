
package com.allen.product.infrastructure.persistence.adapters;

import com.allen.event_contracts.event.StockUpdateEvent;
import com.allen.product.application.usecase.StockEventPublisherUsecase;
import com.allen.product.domain.model.Stock;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class StockEventPublisherAdapter implements StockEventPublisherUsecase{

    private final StreamBridge streamBridge;

    public StockEventPublisherAdapter(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }
    @Override
    public void publish(StockUpdateEvent event) {
        streamBridge.send("stock-out-0", event);
    }
}

