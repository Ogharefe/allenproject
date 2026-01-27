package com.allen.product.application.applicationMapper;

import com.allen.product.application.dto.ProductDto;
import com.allen.product.application.dto.StockDTO;
import com.allen.product.domain.model.Product;
import com.allen.product.domain.model.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppStockMapper {

    Stock stockDTOToDomain(StockDTO stockDTO);
    StockDTO stockDomainToDTO(Stock domain);
}
