package com.allen.sales.infrastructure.web.rest;

import com.allen.sales.application.appMappers.AppMapper;
import com.allen.sales.application.requestDataDto.PaymentRequesttDto;
import com.allen.sales.application.requestDataDto.SaleItemRequestDto;
import com.allen.sales.application.requestDataDto.SaleRequestDto;
import com.allen.sales.application.usecase.SaleUseCase;
import com.allen.sales.domain.model.Payment;
import com.allen.sales.domain.model.ProductSnapshot;
import com.allen.sales.domain.model.Sale;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final SaleUseCase saleUseCase;
    private final AppMapper appMapper;

    public SaleController(SaleUseCase saleUseCase, AppMapper appMapper) {
        this.saleUseCase = saleUseCase;
        this.appMapper = appMapper;
    }

    @PostMapping
    public ResponseEntity<SaleRequestDto> createSale(@RequestBody @Valid SaleRequestDto request) {
        Sale created = saleUseCase.createSale(appMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(appMapper.toDto(created));
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<SaleRequestDto> getSale(@PathVariable UUID saleId) {
        return ResponseEntity.ok(appMapper.toDto(saleUseCase.getSale(saleId)));
    }

    @DeleteMapping("/{saleId}")
    public ResponseEntity<Void> deleteSale(@PathVariable UUID saleId) {
        saleUseCase.deleteSale(saleId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{saleId}/items")
    public ResponseEntity<SaleRequestDto> addItemToSales(@PathVariable UUID saleId,@RequestBody @Valid SaleItemRequestDto request) {
        ProductSnapshot snapshot = new ProductSnapshot(
                request.productId(),
                request.productName(),
                request.description(),
                request.unitPrice(),
                request.sku()
        );

        Sale updated = saleUseCase.addItem(saleId, snapshot, request.quantity());
        return ResponseEntity.ok(appMapper.toDto(updated));
    }

    @DeleteMapping("/{saleId}/items/{productId}")
    public ResponseEntity<SaleRequestDto> removeItem(@PathVariable UUID saleId,@PathVariable UUID productId) {
        Sale updated = saleUseCase.removeItem(saleId, productId);
        return ResponseEntity.ok(appMapper.toDto(updated));
    }

    @PatchMapping("/{saleId}/items/{productId}")
    public ResponseEntity<SaleRequestDto> updateItemQuantity(
            @PathVariable UUID saleId,
            @PathVariable UUID productId,
            @RequestBody @Valid UpdateQuantityRequest request
    ) {
        Sale updated = saleUseCase.updateItemQuantity(saleId, productId, request.quantity());
        return ResponseEntity.ok(appMapper.toDto(updated));
    }

    @PostMapping("/{saleId}/complete")
    public ResponseEntity<SaleRequestDto> completeSale(
            @PathVariable UUID saleId,
            @RequestBody @Valid PaymentRequesttDto paymentRequest
    ) {
        Payment payment = appMapper.toDomain(paymentRequest);
        Sale updated = saleUseCase.completeSale(saleId, payment);
        return ResponseEntity.ok(appMapper.toDto(updated));
    }

    @PostMapping("/{saleId}/cancel")
    public ResponseEntity<SaleRequestDto> cancelSale(@PathVariable UUID saleId) {
        Sale updated = saleUseCase.cancelSale(saleId);
        return ResponseEntity.ok(appMapper.toDto(updated));
    }

    public record UpdateQuantityRequest(@Min(1) int quantity) { }
}