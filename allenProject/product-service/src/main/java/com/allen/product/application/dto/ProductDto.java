package com.allen.product.application.dto;

import java.util.List;

public record ProductDto(
                         Long productId,
                         String sku,
                         String name,
                         String description,
                         double price,
                        List<StockDTO> stocks) {}

