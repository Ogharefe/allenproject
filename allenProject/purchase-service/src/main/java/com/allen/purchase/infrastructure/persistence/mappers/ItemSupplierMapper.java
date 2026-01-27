package com.allen.purchase.infrastructure.persistence.mappers;

import com.allen.purchase.domain.model.ItemSupplier;
import com.allen.purchase.infrastructure.persistence.entity.ItemSupplierEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemSupplierMapper {
    ItemSupplier fromEntityToItemSupplierDomain(ItemSupplierEntity entity);
    ItemSupplierEntity fromDomainToSupplierEntity(ItemSupplier domain);

}
