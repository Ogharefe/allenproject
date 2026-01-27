package com.allen.sales.domain.port;

import com.allen.event_contracts.enums.SaleStatus;
import com.allen.sales.domain.model.Sale;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleRepositoryPort {

    // Basic CRUD
    Sale save(Sale sale);
    Optional<Sale> findById(UUID saleId);
    List<Sale> findAll();
    boolean existsById(UUID saleId);    // Queries by status
    List<Sale> findByStatus(SaleStatus status);    // Queries by date
    List<Sale> findByDateBetween(LocalDateTime start, LocalDateTime end);
    // Customer-related queries
    List<Sale> findByCustomerId(UUID customerId);
    List<Sale> findByCustomerIdAndStatus(UUID customerId, SaleStatus status);
    // Domain-specific: update status (e.g. from PENDING -> COMPLETED / CANCELLED)
    Sale updateStatus(UUID saleId, SaleStatus newStatus);
    void deleteSaleById(UUID saleId);
}