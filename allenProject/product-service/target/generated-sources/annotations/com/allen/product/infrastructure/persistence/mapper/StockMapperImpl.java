package com.allen.product.infrastructure.persistence.mapper;

import com.allen.product.domain.model.Stock;
import com.allen.product.infrastructure.persistence.entity.ProductEntity;
import com.allen.product.infrastructure.persistence.entity.StockEntity;
import com.allen.product.infrastructure.persistence.entity.WarehouseEntity;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T05:58:45+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public Stock stockEntityToStock(StockEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long productId = null;
        Long warehouseId = null;
        String warehouseName = null;
        Long stockId = null;
        Integer quantityOnHand = null;
        Integer quantityReserved = null;
        LocalDate lastUpdated = null;
        int quantityChange = 0;

        productId = entityProductProductId( entity );
        warehouseId = entityWarehouseWarehouseId( entity );
        warehouseName = entityWarehouseName( entity );
        stockId = entity.getStockId();
        quantityOnHand = entity.getQuantityOnHand();
        quantityReserved = entity.getQuantityReserved();
        lastUpdated = entity.getLastUpdated();
        quantityChange = entity.getQuantityChange();

        Stock stock = new Stock( stockId, productId, warehouseId, warehouseName, quantityOnHand, quantityReserved, lastUpdated, quantityChange );

        return stock;
    }

    @Override
    public StockEntity StockToStockEntity(Stock stock) {
        if ( stock == null ) {
            return null;
        }

        StockEntity stockEntity = new StockEntity();

        stockEntity.setProduct( productEntityFromId( stock.productId() ) );
        stockEntity.setWarehouse( warehouseEntityFromId( stock.warehouseId() ) );
        stockEntity.setStockId( stock.stockId() );
        stockEntity.setQuantityOnHand( stock.quantityOnHand() );
        stockEntity.setQuantityReserved( stock.quantityReserved() );
        stockEntity.setLastUpdated( stock.lastUpdated() );
        stockEntity.setQuantityChange( stock.quantityChange() );

        return stockEntity;
    }

    private Long entityProductProductId(StockEntity stockEntity) {
        ProductEntity product = stockEntity.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getProductId();
    }

    private Long entityWarehouseWarehouseId(StockEntity stockEntity) {
        WarehouseEntity warehouse = stockEntity.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        return warehouse.getWarehouseId();
    }

    private String entityWarehouseName(StockEntity stockEntity) {
        WarehouseEntity warehouse = stockEntity.getWarehouse();
        if ( warehouse == null ) {
            return null;
        }
        return warehouse.getName();
    }
}
