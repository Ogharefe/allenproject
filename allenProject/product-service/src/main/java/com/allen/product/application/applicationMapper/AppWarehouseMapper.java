package com.allen.product.application.applicationMapper;

import com.allen.product.application.dto.WarehouseDTO;
import com.allen.product.domain.model.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppWarehouseMapper {

    Warehouse warehouseDtoToWarehouseDomain(WarehouseDTO warehouseDTO);
    WarehouseDTO warehousDomainToWarehouseDTO(Warehouse domain);
}
