package com.allen.purchase.applications.dtos;

public record ItemSupplierDTO(
        Long supplierId,
        String name,
        String contactPerson,
        String email,
        String phoneNumber,
        String address
) {
}
