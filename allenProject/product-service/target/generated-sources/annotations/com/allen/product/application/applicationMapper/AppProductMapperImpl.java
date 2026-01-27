package com.allen.product.application.applicationMapper;

import com.allen.product.application.dto.ProductDto;
import com.allen.product.application.dto.StockDTO;
import com.allen.product.domain.model.Product;
import com.allen.product.domain.model.Stock;
import java.time.LocalDate;
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
public class AppProductMapperImpl implements AppProductMapper {

    @Override
    public ProductDto productToProductDto(Product domain) {
        if ( domain == null ) {
            return null;
        }

        Long productId = null;
        String sku = null;
        String name = null;
        String description = null;
        double price = 0.0d;
        List<StockDTO> stocks = null;

        productId = domain.productId();
        sku = domain.sku();
        name = domain.name();
        description = domain.description();
        price = domain.price();
        stocks = stockListToStockDTOList( domain.stocks() );

        ProductDto productDto = new ProductDto( productId, sku, name, description, price, stocks );

        return productDto;
    }

    @Override
    public Product productDtoToProduct(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Long productId = null;
        String name = null;
        String description = null;
        double price = 0.0d;
        String sku = null;
        List<Stock> stocks = null;

        productId = dto.productId();
        name = dto.name();
        description = dto.description();
        price = dto.price();
        sku = dto.sku();
        stocks = stockDTOListToStockList( dto.stocks() );

        Product product = new Product( productId, name, description, price, sku, stocks );

        return product;
    }

    protected StockDTO stockToStockDTO(Stock stock) {
        if ( stock == null ) {
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

        stockId = stock.stockId();
        productId = stock.productId();
        warehouseId = stock.warehouseId();
        warehouseName = stock.warehouseName();
        quantityOnHand = stock.quantityOnHand();
        quantityReserved = stock.quantityReserved();
        lastUpdated = stock.lastUpdated();
        quantityChange = stock.quantityChange();

        String productSku = null;

        StockDTO stockDTO = new StockDTO( stockId, productId, productSku, warehouseId, warehouseName, quantityOnHand, quantityReserved, lastUpdated, quantityChange );

        return stockDTO;
    }

    protected List<StockDTO> stockListToStockDTOList(List<Stock> list) {
        if ( list == null ) {
            return null;
        }

        List<StockDTO> list1 = new ArrayList<StockDTO>( list.size() );
        for ( Stock stock : list ) {
            list1.add( stockToStockDTO( stock ) );
        }

        return list1;
    }

    protected Stock stockDTOToStock(StockDTO stockDTO) {
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

    protected List<Stock> stockDTOListToStockList(List<StockDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Stock> list1 = new ArrayList<Stock>( list.size() );
        for ( StockDTO stockDTO : list ) {
            list1.add( stockDTOToStock( stockDTO ) );
        }

        return list1;
    }
}
