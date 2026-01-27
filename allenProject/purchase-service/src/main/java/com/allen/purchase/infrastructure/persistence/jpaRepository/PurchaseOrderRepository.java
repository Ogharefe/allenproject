package com.allen.purchase.infrastructure.persistence.jpaRepository;

import com.allen.purchase.infrastructure.persistence.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity,Long> {


}
