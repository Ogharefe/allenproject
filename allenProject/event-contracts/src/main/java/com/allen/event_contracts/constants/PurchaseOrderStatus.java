package com.allen.event_contracts.constants;

public enum PurchaseOrderStatus {
    NEW,            // Order just created, not yet reviewed
    APPROVED,       // Approved internally (e.g., by procurement manager)
    SENT,           // Sent to supplier (awaiting supplier confirmation)
    CONFIRMED,      // Supplier confirmed the order
    PARTIALLY_RECEIVED, // Some items received, others pending
    RECEIVED,       // All items received into warehouse
    CANCELLED,      // Order was cancelled (before completion)
    CLOSED          // Order finalized (payment done, archived)
}
