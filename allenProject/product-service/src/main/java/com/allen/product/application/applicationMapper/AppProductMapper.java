package com.allen.product.application.applicationMapper;

import com.allen.product.application.dto.ProductDto;
import com.allen.product.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppProductMapper {

    ProductDto productToProductDto(Product domain);
    Product productDtoToProduct(ProductDto dto);
}
