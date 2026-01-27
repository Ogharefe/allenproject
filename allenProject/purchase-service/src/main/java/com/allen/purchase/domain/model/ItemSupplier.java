package com.allen.purchase.domain.model;

public record ItemSupplier(
        Long supplierId,
        String name,
        String contactPerson,
        String email,
        String phoneNumber,
        String address
) {
}
