package com.allen.event_contracts.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventMetadata(
        UUID eventId,
        LocalDateTime timestamp,
        String eventType
) {
    public static EventMetadata create(String eventType) {
        return new EventMetadata(UUID.randomUUID(), LocalDateTime.now(), eventType);
    }
}