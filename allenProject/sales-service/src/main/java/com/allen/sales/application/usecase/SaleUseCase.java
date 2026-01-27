package com.allen.sales.application.usecase;

import com.allen.sales.domain.model.Payment;
import com.allen.sales.domain.model.ProductSnapshot;
import com.allen.sales.domain.model.Sale;

import java.util.UUID;

public interface SaleUseCase {
    Sale createSale(Sale sale);
    Sale addItem(UUID saleId, ProductSnapshot product, int quantity);
    Sale removeItem(UUID saleId, UUID saleItemId);
    Sale updateItemQuantity(UUID saleId, UUID saleItemId, int quantity);
    Sale completeSale(UUID saleId, Payment payment);
    Sale cancelSale(UUID saleId);
    Sale getSale(UUID saleId);
    void deleteSale(UUID saleId);
}
