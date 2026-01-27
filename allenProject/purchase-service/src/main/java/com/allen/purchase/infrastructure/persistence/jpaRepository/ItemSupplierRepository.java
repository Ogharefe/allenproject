package com.allen.purchase.infrastructure.persistence.jpaRepository;

import com.allen.purchase.infrastructure.persistence.entity.ItemSupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSupplierRepository extends JpaRepository<ItemSupplierEntity,Long> {
}
