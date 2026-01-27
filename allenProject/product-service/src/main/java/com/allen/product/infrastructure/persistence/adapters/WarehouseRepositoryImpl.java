package com.allen.product.infrastructure.persistence.adapters;

import com.allen.product.domain.model.Warehouse;
import com.allen.product.domain.port.WarehouseRepositoryPort;
import com.allen.product.infrastructure.persistence.entity.WarehouseEntity;
import com.allen.product.infrastructure.persistence.mapper.WarehouseMapper;
import com.allen.product.infrastructure.persistence.springdataJpaRepository.WarehouseRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Lazy
public class WarehouseRepositoryImpl implements WarehouseRepositoryPort {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public WarehouseRepositoryImpl(@Lazy WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    public Optional<Warehouse> findByWarehouseId(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .map(warehouseMapper::warehouseEntityToWarehouse);
    }

    @Override
    public Warehouse create(Warehouse warehouse) {
        WarehouseEntity warehouseEntity = warehouseMapper.warehouseToWarehouseEntity(warehouse);
        return warehouseMapper.warehouseEntityToWarehouse(warehouseRepository.save(warehouseEntity));
    }

    @Override
    public List<Warehouse> findAllWarehouse() {
        return warehouseRepository.findAll().stream()
                .map(warehouseMapper::warehouseEntityToWarehouse)
                .toList();
    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }
}
