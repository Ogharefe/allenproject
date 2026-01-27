package com.allen.purchase.infrastructure.persistence.jpaRepository;

import com.allen.purchase.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderLineRepos extends JpaRepository<PurchaseOrderLineEntity,Long> {
}
