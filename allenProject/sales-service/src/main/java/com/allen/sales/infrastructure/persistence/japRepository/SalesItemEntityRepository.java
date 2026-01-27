package com.allen.sales.infrastructure.persistence.japRepository;

import com.allen.sales.infrastructure.persistence.entity.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalesItemEntityRepository extends JpaRepository<SaleItemEntity, UUID>
{
}
