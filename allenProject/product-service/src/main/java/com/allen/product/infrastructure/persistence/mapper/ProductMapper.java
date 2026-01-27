package com.allen.product.infrastructure.persistence.mapper;

import com.allen.product.domain.model.Product;
import com.allen.product.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StockMapper.class})
public interface ProductMapper {

    @Mapping(target = "stocks", ignore = true)
    Product productEntityToProduct(ProductEntity entity);

    @Mapping(target = "stocks", ignore = true)
    ProductEntity productToEntity(Product product);
}
