package com.allen.product.application.applicationMapper;

import com.allen.product.application.dto.WarehouseDTO;
import com.allen.product.domain.model.Warehouse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T05:58:45+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class AppWarehouseMapperImpl implements AppWarehouseMapper {

    @Override
    public Warehouse warehouseDtoToWarehouseDomain(WarehouseDTO warehouseDTO) {
        if ( warehouseDTO == null ) {
            return null;
        }

        Long warehouseId = null;
        String name = null;
        String location = null;
        String managerName = null;
        String contactInfo = null;

        warehouseId = warehouseDTO.warehouseId();
        name = warehouseDTO.name();
        location = warehouseDTO.location();
        managerName = warehouseDTO.managerName();
        contactInfo = warehouseDTO.contactInfo();

        Warehouse warehouse = new Warehouse( warehouseId, name, location, managerName, contactInfo );

        return warehouse;
    }

    @Override
    public WarehouseDTO warehousDomainToWarehouseDTO(Warehouse domain) {
        if ( domain == null ) {
            return null;
        }

        Long warehouseId = null;
        String name = null;
        String location = null;
        String managerName = null;
        String contactInfo = null;

        warehouseId = domain.warehouseId();
        name = domain.name();
        location = domain.location();
        managerName = domain.managerName();
        contactInfo = domain.contactInfo();

        WarehouseDTO warehouseDTO = new WarehouseDTO( warehouseId, name, location, managerName, contactInfo );

        return warehouseDTO;
    }
}
