package com.allen.product.domain.model;

import java.util.List;

public record Product (

        Long productId,
        String name,
        String description,
        double price,
        String sku,
        List<Stock> stocks){}

