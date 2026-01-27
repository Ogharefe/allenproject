package com.allen.purchase.infrastructure.persistence.mappers;

import com.allen.purchase.domain.model.PurchaseOrder;
import com.allen.purchase.domain.model.PurchaseOrderLine;
import com.allen.purchase.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.allen.purchase.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {

    @Mapping(target = "orderLines", ignore = true) // handled manually
    @Mapping(target = "supplierName", source = "supplierName") // ✅ explicitly mapped
    PurchaseOrderEntity purchaseOrderToEntity(PurchaseOrder domain);
    @Mapping(target = "supplierName", source = "supplierName") // ✅ explicitly mapped
    PurchaseOrder purchaseEntityToDomain(PurchaseOrderEntity entity);

    PurchaseOrderLineEntity purchaseOrderLineToEntity(PurchaseOrderLine domain);
    PurchaseOrderLine purchaseOrderLineEntityToDomain(PurchaseOrderLineEntity entity);

    /**
     * Custom method to set the bidirectional relationship properly.
     */
    default PurchaseOrderEntity toEntityWithRelations(PurchaseOrder domain) {
        PurchaseOrderEntity entity = purchaseOrderToEntity(domain);
        if (domain.orderLines() != null) {
            List<PurchaseOrderLineEntity> lineEntities = domain.orderLines().stream()
                    .map(this::purchaseOrderLineToEntity)
                    .peek(lineEntity -> lineEntity.setPurchaseOrder(entity)) // ✅ critical link
                    .toList();
            entity.setOrderLines(lineEntities);
        }
        return entity;
    }
}



