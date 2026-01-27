package com.allen.sales.application.appMappers;

import com.allen.sales.application.requestDataDto.PaymentRequesttDto;
import com.allen.sales.application.requestDataDto.SaleItemRequestDto;
import com.allen.sales.application.requestDataDto.SaleRequestDto;
import com.allen.sales.domain.model.Payment;
import com.allen.sales.domain.model.Sale;
import com.allen.sales.domain.model.SaleItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface AppMapper {

    // ===== SaleRequestDto <-> Sale =====
    @Mapping(target = "payment", ignore = true) // SaleRequestDto has no payment
    Sale toDomain(SaleRequestDto dto);

    @InheritInverseConfiguration
    SaleRequestDto toDto(Sale domain);

    // ===== SaleItemRequestDto <-> SaleItem =====
    @Mapping(target = "idSaleItem", expression = "java(UUID.randomUUID())") // request doesn't contain item id
    SaleItem toDomain(SaleItemRequestDto dto);

    @InheritInverseConfiguration
    @Mapping(target = "sku", ignore = true) // domain doesn't contain sku
    SaleItemRequestDto toDto(SaleItem domain);

    // ===== PaymentRequesttDto <-> Payment =====
    Payment toDomain(PaymentRequesttDto dto);

    @InheritInverseConfiguration
    PaymentRequesttDto toDto(Payment domain);
}