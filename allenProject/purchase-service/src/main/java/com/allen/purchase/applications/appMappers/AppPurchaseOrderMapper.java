package com.allen.purchase.applications.appMappers;

import com.allen.purchase.applications.dtos.PurchaseOrderDTO;
import com.allen.purchase.applications.dtos.PurchaseOrderLineDTO;
import com.allen.purchase.domain.model.PurchaseOrder;
import com.allen.purchase.domain.model.PurchaseOrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppPurchaseOrderMapper {

    @Mapping(target = "orderLines", expression = "java(order.orderLines().stream().map(this::toDtoWithTotalPrice).toList())")
    PurchaseOrderDTO purchaseOrderToDTO(PurchaseOrder order);
    @Mapping(target = "orderLines", expression = "java(toDomainWithTotalPrice(dto.orderLines()))")
    PurchaseOrder purchaseOrderDTOToDomain(PurchaseOrderDTO dto);

    PurchaseOrderLineDTO toDto(PurchaseOrderLine line);
    PurchaseOrderLine toDomain(PurchaseOrderLineDTO dto);

    default PurchaseOrderLineDTO toDtoWithTotalPrice(PurchaseOrderLine line) {
        double total = line.totalPrice() == 0
                ? line.unitPrice() * line.quantityOrdered()
                : line.totalPrice();

        return new PurchaseOrderLineDTO(
                line.lineId(),
                line.productId(),
                line.warehouseId(),
                line.quantityOrdered(),
                line.unitPrice(),
                total
        );
    }

    default List<PurchaseOrderLine> toDomainWithTotalPrice(List<PurchaseOrderLineDTO> lines) {
        if (lines == null) return List.of();
        return lines.stream()
                .map(line -> new PurchaseOrderLine(
                        line.lineId(),
                        line.productId(),
                        line.warehouseId(),
                        line.quantityOrdered(),
                        line.unitPrice(),
                        line.unitPrice() * line.quantityOrdered()
                ))
                .toList();
    }
}

