package com.allen.purchase.applications.service;

import org.springframework.stereotype.Service;

@Service
public class StockService {
    // Stock updates are now handled directly by product-service
    // when it consumes PurchaseOrderEvent
}
