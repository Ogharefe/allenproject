package com.allen.sales.infrastructure.persistence.mapper;

import com.allen.sales.domain.model.ProductSnapshot;
import com.allen.sales.infrastructure.persistence.entity.ProductSnapshotEntity;
import java.math.BigDecimal;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T05:58:58+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class ProductSnapshotMapperImpl implements ProductSnapshotMapper {

    @Override
    public ProductSnapshot toDomain(ProductSnapshotEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID productId = null;
        String name = null;
        String description = null;
        BigDecimal unitPrice = null;
        String sku = null;

        productId = entity.getProductId();
        name = entity.getName();
        description = entity.getDescription();
        unitPrice = entity.getUnitPrice();
        sku = entity.getSku();

        ProductSnapshot productSnapshot = new ProductSnapshot( productId, name, description, unitPrice, sku );

        return productSnapshot;
    }

    @Override
    public ProductSnapshotEntity toEntity(ProductSnapshot snapshot) {
        if ( snapshot == null ) {
            return null;
        }

        UUID productId = null;
        String name = null;
        String description = null;
        BigDecimal unitPrice = null;
        String sku = null;

        productId = snapshot.productId();
        name = snapshot.name();
        description = snapshot.description();
        unitPrice = snapshot.unitPrice();
        sku = snapshot.sku();

        ProductSnapshotEntity productSnapshotEntity = new ProductSnapshotEntity( productId, name, description, unitPrice, sku );

        return productSnapshotEntity;
    }

    @Override
    public void updateEntity(ProductSnapshot snapshot, ProductSnapshotEntity entity) {
        if ( snapshot == null ) {
            return;
        }

        entity.setProductId( snapshot.productId() );
        entity.setName( snapshot.name() );
        entity.setDescription( snapshot.description() );
        entity.setUnitPrice( snapshot.unitPrice() );
        entity.setSku( snapshot.sku() );
    }
}
