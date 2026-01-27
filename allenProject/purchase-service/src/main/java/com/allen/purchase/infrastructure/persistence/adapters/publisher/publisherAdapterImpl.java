package com.allen.purchase.infrastructure.persistence.adapters.publisher;

import com.allen.event_contracts.event.PurchaseOrderEvent;
import com.allen.purchase.applications.publisherUsecase.PurchaseOrderEventUsecase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class publisherAdapterImpl implements PurchaseOrderEventUsecase {

    private static final Logger log = LoggerFactory.getLogger(publisherAdapterImpl.class);
    private final StreamBridge streamBridge;

    public publisherAdapterImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publish(PurchaseOrderEvent event) {
        streamBridge.send("purchaseOrderEvents-out-0",event);
    }
}
