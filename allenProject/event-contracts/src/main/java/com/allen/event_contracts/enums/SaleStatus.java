package com.allen.event_contracts.enums;

public enum SaleStatus {

    DRAFT,          // Sale created but not yet confirmed (items can be added/removed)
    PENDING_PAYMENT, // Sale confirmed but waiting for payment
    PAID,           // Payment completed successfully
    COMPLETED,      // Sale finalized (invoice generated, stock updated)
    CANCELLED,      // Sale cancelled (possibly refunded, stock released)
    FAILED_PAYMENT
}
