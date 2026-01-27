package com.allen.sales.infrastructure.persistence.japRepository;

import com.allen.event_contracts.enums.SaleStatus;
import com.allen.sales.infrastructure.persistence.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalesEntityRepository extends JpaRepository<SaleEntity, UUID> {

    List<SaleEntity> findByStatus(SaleStatus status);

    List<SaleEntity> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);

    List<SaleEntity> findByCustomerId(UUID customerId);

    List<SaleEntity> findByCustomerIdAndStatus(UUID customerId, SaleStatus status);

    Optional<SaleEntity> findByIdSale(UUID saleId);
}
