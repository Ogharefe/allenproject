package com.allen.product.infrastructure.web.rest;

import com.allen.event_contracts.event.StockUpdateCommand;
import com.allen.event_contracts.event.StockUpdateCommandEvent;
import com.allen.product.application.usecase.StockUseCase;
import com.allen.product.domain.model.Stock;
import com.allen.product.application.dto.StockDTO;
import com.allen.product.application.applicationMapper.AppStockMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockUseCase stockUseCase;
    private final AppStockMapper mapper;
    private final StreamBridge streamBridge;

    private static final Logger log = LoggerFactory.getLogger(StockController.class);

    public StockController(StockUseCase stockUseCase, AppStockMapper mapper, StreamBridge streamBridge) {
        this.stockUseCase = stockUseCase;
        this.mapper = mapper;
        this.streamBridge = streamBridge;
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getStockList() {
        List<Stock> stocks = stockUseCase.getStockList();
        log.info("Retrieved {} stocks", stocks.size());
        List<StockDTO> stockDTOs = stocks.stream()
                .map(mapper::stockDomainToDTO)
                .toList();
        return ResponseEntity.ok(stockDTOs);
    }

    @PostMapping("/{stockId}/reserve")
    public ResponseEntity<StockDTO> reserveStock(
            @PathVariable Long stockId,
            @RequestParam int quantityToReserve) {

        Stock reserved = stockUseCase.reserveStock(quantityToReserve,stockId);
        return ResponseEntity.ok(mapper.stockDomainToDTO(reserved));
    }

    @DeleteMapping("/{stockId}")
    public  ResponseEntity<Void> deletStock(@PathVariable Long stockId){

        stockUseCase.deletStock(stockId);
        return ResponseEntity.noContent().build();

    }
}
