package com.allen.event_contracts.enums;

public enum StockUpdateType {
    PURCHASE,   // Increase quantity
    SALE,       // Decrease quantity
    RESERVATION, // Decrease quantity (reserved)
    RELEASE_RESERVATION
}