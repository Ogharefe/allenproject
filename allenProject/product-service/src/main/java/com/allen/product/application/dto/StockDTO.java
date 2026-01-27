package com.allen.product.application.dto;

import java.time.LocalDate;

public record StockDTO(

               Long stockId,
               Long productId,
               String productSku,
               Long warehouseId,
               String warehouseName,
               Integer quantityOnHand,
               Integer quantityReserved,
               LocalDate lastUpdated,
               int quantityChange


){}
