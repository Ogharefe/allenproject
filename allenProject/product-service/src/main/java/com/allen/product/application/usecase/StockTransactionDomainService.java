package com.allen.product.application.usecase;

import com.allen.event_contracts.enums.StockUpdateType;
import com.allen.product.domain.model.Stock;
import com.allen.product.domain.model.StockTransaction;

public interface StockTransactionDomainService {
    StockTransaction recordTransaction(Stock stock, StockUpdateType type, int quantityChange);
}