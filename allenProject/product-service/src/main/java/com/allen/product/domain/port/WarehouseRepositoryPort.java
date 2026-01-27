package com.allen.product.domain.port;

import com.allen.product.domain.model.Warehouse;

import java.util.List;
import java.util.Optional;


public interface WarehouseRepositoryPort {

    Optional<Warehouse> findByWarehouseId(Long idWarehouse);
    Warehouse create(Warehouse warehouse);
    List<Warehouse> findAllWarehouse();
    void deleteWarehouse(Long warehouseId);
}
