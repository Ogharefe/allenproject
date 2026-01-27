package com.allen.product.domain.model;

public record Warehouse(
    Long warehouseId,
    String name,
    String location,
    String managerName,
    String contactInfo
) {}
