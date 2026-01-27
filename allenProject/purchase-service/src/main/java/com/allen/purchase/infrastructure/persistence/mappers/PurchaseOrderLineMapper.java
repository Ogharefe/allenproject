package com.allen.purchase.infrastructure.persistence.mappers;

import com.allen.purchase.domain.model.PurchaseOrderLine;
import com.allen.purchase.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderLineMapper {

    PurchaseOrderLineEntity toEntity(PurchaseOrderLine domain);
    PurchaseOrderLine toDomain(PurchaseOrderLineEntity entity);
}
