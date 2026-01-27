package main.java.com.allen.product.application.facade;

import com.allen.product.application.constants.StockUpdateType;
import com.allen.product.domain.model.Stock;
import com.allen.product.domain.model.StockTransaction;
import com.allen.product.application.service.StockTransactionService;
import org.springframework.stereotype.Component;

@Component
public class StockTransactionFacade {
    private final StockTransactionService transactionService;

    public StockTransactionFacade(StockTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void recordStockUpdate(Stock stock, StockUpdateType type, int quantityChange) {
        transactionService.recordTransaction(stock, type, quantityChange);
    }
}