package com.allen.product.application.applicationMapper;

import com.allen.product.application.dto.StockDTO;
import com.allen.product.domain.model.Stock;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T05:58:44+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class AppStockMapperImpl implements AppStockMapper {

    @Override
    public Stock stockDTOToDomain(StockDTO stockDTO) {
        if ( stockDTO == null ) {
            return null;
        }

        Long stockId = null;
        Long productId = null;
        Long warehouseId = null;
        String warehouseName = null;
        Integer quantityOnHand = null;
        Integer quantityReserved = null;
        LocalDate lastUpdated = null;
        int quantityChange = 0;

        stockId = stockDTO.stockId();
        productId = stockDTO.productId();
        warehouseId = stockDTO.warehouseId();
        warehouseName = stockDTO.warehouseName();
        quantityOnHand = stockDTO.quantityOnHand();
        quantityReserved = stockDTO.quantityReserved();
        lastUpdated = stockDTO.lastUpdated();
        quantityChange = stockDTO.quantityChange();

        Stock stock = new Stock( stockId, productId, warehouseId, warehouseName, quantityOnHand, quantityReserved, lastUpdated, quantityChange );

        return stock;
    }

    @Override
    public StockDTO stockDomainToDTO(Stock domain) {
        if ( domain == null ) {
            return null;
        }

        Long stockId = null;
        Long productId = null;
        Long warehouseId = null;
        String warehouseName = null;
        Integer quantityOnHand = null;
        Integer quantityReserved = null;
        LocalDate lastUpdated = null;
        int quantityChange = 0;

        stockId = domain.stockId();
        productId = domain.productId();
        warehouseId = domain.warehouseId();
        warehouseName = domain.warehouseName();
        quantityOnHand = domain.quantityOnHand();
        quantityReserved = domain.quantityReserved();
        lastUpdated = domain.lastUpdated();
        quantityChange = domain.quantityChange();

        String productSku = null;

        StockDTO stockDTO = new StockDTO( stockId, productId, productSku, warehouseId, warehouseName, quantityOnHand, quantityReserved, lastUpdated, quantityChange );

        return stockDTO;
    }
}
