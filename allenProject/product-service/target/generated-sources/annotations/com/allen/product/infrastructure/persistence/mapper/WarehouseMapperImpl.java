package com.allen.product.infrastructure.persistence.mapper;

import com.allen.product.domain.model.Warehouse;
import com.allen.product.infrastructure.persistence.entity.WarehouseEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T05:58:45+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class WarehouseMapperImpl implements WarehouseMapper {

    @Override
    public Warehouse warehouseEntityToWarehouse(WarehouseEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long warehouseId = null;
        String name = null;
        String location = null;
        String managerName = null;
        String contactInfo = null;

        warehouseId = entity.getWarehouseId();
        name = entity.getName();
        location = entity.getLocation();
        managerName = entity.getManagerName();
        contactInfo = entity.getContactInfo();

        Warehouse warehouse = new Warehouse( warehouseId, name, location, managerName, contactInfo );

        return warehouse;
    }

    @Override
    public WarehouseEntity warehouseToWarehouseEntity(Warehouse warehouse) {
        if ( warehouse == null ) {
            return null;
        }

        WarehouseEntity warehouseEntity = new WarehouseEntity();

        warehouseEntity.setWarehouseId( warehouse.warehouseId() );
        warehouseEntity.setName( warehouse.name() );
        warehouseEntity.setLocation( warehouse.location() );
        warehouseEntity.setManagerName( warehouse.managerName() );
        warehouseEntity.setContactInfo( warehouse.contactInfo() );

        return warehouseEntity;
    }
}
