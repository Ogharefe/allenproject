package com.allen.sales.infrastructure.persistence.mapper;

import com.allen.sales.domain.model.ProductSnapshot;
import com.allen.sales.infrastructure.persistence.entity.ProductSnapshotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductSnapshotMapper {

    ProductSnapshot toDomain(ProductSnapshotEntity entity);
    ProductSnapshotEntity toEntity(ProductSnapshot snapshot);
    void updateEntity(ProductSnapshot snapshot, @MappingTarget ProductSnapshotEntity entity);
}