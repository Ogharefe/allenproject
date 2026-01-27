package com.allen.product.infrastructure.persistence.mapper;

import com.allen.product.domain.model.StockTransaction;
import com.allen.product.infrastructure.persistence.entity.StockTransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockTransactionMapper {
    StockTransaction toDomain(StockTransactionEntity entity);
    StockTransactionEntity toEntity(StockTransaction domain);
    List<StockTransaction> toDomainList(List<StockTransactionEntity> entities);
}