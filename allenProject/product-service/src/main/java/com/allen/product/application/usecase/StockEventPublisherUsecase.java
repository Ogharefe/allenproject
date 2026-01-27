package com.allen.product.application.usecase;


import com.allen.event_contracts.event.StockUpdateEvent;

public interface StockEventPublisherUsecase {
    void publish(StockUpdateEvent event);
}
