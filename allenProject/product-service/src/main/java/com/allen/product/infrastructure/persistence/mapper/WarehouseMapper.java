package com.allen.product.infrastructure.persistence.mapper;

import com.allen.product.application.dto.WarehouseDTO;
import com.allen.product.domain.model.Warehouse;
import com.allen.product.infrastructure.persistence.entity.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @Mappings({
            @Mapping(target = "warehouseId", source = "warehouseId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "location", source = "location"),
            @Mapping(target = "managerName", source = "managerName"),
            @Mapping(target = "contactInfo", source = "contactInfo")
    })
    Warehouse warehouseEntityToWarehouse(WarehouseEntity entity);

    @Mappings({
            @Mapping(target = "warehouseId", source = "warehouseId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "location", source = "location"),
            @Mapping(target = "managerName", source = "managerName"),
            @Mapping(target = "contactInfo", source = "contactInfo")
    })
    WarehouseEntity warehouseToWarehouseEntity(Warehouse warehouse);

}