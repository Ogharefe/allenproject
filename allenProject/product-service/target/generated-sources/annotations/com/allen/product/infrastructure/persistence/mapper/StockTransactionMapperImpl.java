package com.allen.product.infrastructure.persistence.mapper;

import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.product.domain.model.StockTransaction;
import com.allen.product.infrastructure.persistence.entity.StockTransactionEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T05:58:45+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class StockTransactionMapperImpl implements StockTransactionMapper {

    @Override
    public StockTransaction entityToStockTransaction(StockTransactionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long stockTransactionId = null;
        Long productId = null;
        Long warehouseId = null;
        String warehouseName = null;
        StockUpdateType type = null;
        Integer quantityChange = null;
        Integer resultingQuantity = null;
        LocalDateTime transactionDate = null;

        stockTransactionId = entity.getStockTransactionId();
        productId = entity.getProductId();
        warehouseId = entity.getWarehouseId();
        warehouseName = entity.getWarehouseName();
        type = entity.getType();
        quantityChange = entity.getQuantityChange();
        resultingQuantity = entity.getResultingQuantity();
        transactionDate = entity.getTransactionDate();

        StockTransaction stockTransaction = new StockTransaction( stockTransactionId, productId, warehouseId, warehouseName, type, quantityChange, resultingQuantity, transactionDate );

        return stockTransaction;
    }

    @Override
    public StockTransactionEntity stockTransactionToEntity(StockTransaction domain) {
        if ( domain == null ) {
            return null;
        }

        StockTransactionEntity stockTransactionEntity = new StockTransactionEntity();

        stockTransactionEntity.setStockTransactionId( domain.stockTransactionId() );
        stockTransactionEntity.setProductId( domain.productId() );
        stockTransactionEntity.setWarehouseId( domain.warehouseId() );
        stockTransactionEntity.setWarehouseName( domain.warehouseName() );
        stockTransactionEntity.setType( domain.type() );
        stockTransactionEntity.setQuantityChange( domain.quantityChange() );
        stockTransactionEntity.setResultingQuantity( domain.resultingQuantity() );
        stockTransactionEntity.setTransactionDate( domain.transactionDate() );

        return stockTransactionEntity;
    }

    @Override
    public List<StockTransaction> listEntityToStockTransactionList(List<StockTransactionEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<StockTransaction> list = new ArrayList<StockTransaction>( entities.size() );
        for ( StockTransactionEntity stockTransactionEntity : entities ) {
            list.add( entityToStockTransaction( stockTransactionEntity ) );
        }

        return list;
    }
}
