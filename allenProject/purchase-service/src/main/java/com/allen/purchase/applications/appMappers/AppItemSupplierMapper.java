package com.allen.purchase.applications.appMappers;

import com.allen.purchase.applications.dtos.ItemSupplierDTO;
import com.allen.purchase.domain.model.ItemSupplier;
import com.allen.purchase.infrastructure.persistence.mappers.ItemSupplierMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppItemSupplierMapper {

    ItemSupplier fromDtoToItemSupplierDomain(ItemSupplierDTO dto);
    ItemSupplierDTO fromDomainToItemSupplierDTO(ItemSupplier domain);
}
