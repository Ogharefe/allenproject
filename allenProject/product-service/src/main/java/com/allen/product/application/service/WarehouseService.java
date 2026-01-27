package com.allen.product.application.service;

import com.allen.product.application.usecase.WarehouseUseCase;
import com.allen.product.domain.model.Warehouse;
import com.allen.product.domain.port.WarehouseRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService implements WarehouseUseCase {

    private final WarehouseRepositoryPort port;
    public WarehouseService(WarehouseRepositoryPort port) {
        this.port = port;
    }
    @Override
    public Optional<Warehouse> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Warehouse createWarehouse(Warehouse warehouse) {

        Warehouse newWarehouse = new Warehouse(
              null,
                warehouse.name(),
                warehouse.location(),
                warehouse.managerName(),
                warehouse.contactInfo()
        );
        return port.create(newWarehouse);
    }

    @Override
    public List<Warehouse> getWarehouses() {
        return port.findAllWarehouse();
    }


    @Override
    public Warehouse updateWarehouse(Long warehouseId, Warehouse warehouse) {
        return null;
    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        if (warehouseId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        port.deleteWarehouse(warehouseId);
    }

    @Override
    public void assignManager(Long warehouseId, String managerName) {

    }
}
