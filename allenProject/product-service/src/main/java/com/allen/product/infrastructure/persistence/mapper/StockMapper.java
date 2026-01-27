package com.allen.product.infrastructure.persistence.mapper;

import com.allen.product.domain.model.Stock;
import com.allen.product.infrastructure.persistence.entity.ProductEntity;
import com.allen.product.infrastructure.persistence.entity.StockEntity;
import com.allen.product.infrastructure.persistence.entity.WarehouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface StockMapper {

    @Mappings({
            @Mapping(target = "productId", source = "product.productId"),
            @Mapping(target = "warehouseId", source = "warehouse.warehouseId"),
            @Mapping(target = "warehouseName", source = "warehouse.name"),
            @Mapping(target = "stockId", source = "stockId"),
            @Mapping(target = "quantityOnHand", source = "quantityOnHand"),
            @Mapping(target = "quantityReserved", source = "quantityReserved"),
            @Mapping(target = "lastUpdated", source = "lastUpdated"),
            @Mapping(target = "quantityChange", source = "quantityChange")
    })
    Stock stockEntityToStock(StockEntity entity);
    @Mappings({
            @Mapping(target = "product", source = "productId", qualifiedByName = "productEntityFromId"),
            @Mapping(target = "warehouse", source = "warehouseId", qualifiedByName = "warehouseEntityFromId"),
            @Mapping(target = "stockId", source = "stockId"),
            @Mapping(target = "quantityOnHand", source = "quantityOnHand"),
            @Mapping(target = "quantityReserved", source = "quantityReserved"),
            @Mapping(target = "lastUpdated", source = "lastUpdated"),
            @Mapping(target = "quantityChange", source = "quantityChange")
    })
    StockEntity StockToStockEntity(Stock stock);

    @Named("productEntityFromId")
    default ProductEntity productEntityFromId(Long id) {
        if (id == null) return null;
        ProductEntity entity = new ProductEntity();
        entity.setProductId(id);
        return entity;
    }

    @Named("warehouseEntityFromId")
    default WarehouseEntity warehouseEntityFromId(Long id) {
        if (id == null) return null;
        WarehouseEntity entity = new WarehouseEntity();
        entity.setWarehouseId(id);
        return entity;
    }
}

