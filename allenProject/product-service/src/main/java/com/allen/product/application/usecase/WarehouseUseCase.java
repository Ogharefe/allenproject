package com.allen.product.application.usecase;

import com.allen.product.domain.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseUseCase {

    Optional<Warehouse> findById(Long id);
    Warehouse createWarehouse(Warehouse warehouse);
    List<Warehouse> getWarehouses();
    Warehouse updateWarehouse(Long warehouseId, Warehouse warehouse);
    void deleteWarehouse(Long warehouseId);
    void assignManager(Long warehouseId, String managerName);
}

