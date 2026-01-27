package com.allen.purchase.infrastructure.persistence.adapters;

import com.allen.purchase.domain.model.PurchaseOrderLine;
import com.allen.purchase.domain.port.PurchaseOrderLineReposPort;
import com.allen.purchase.infrastructure.persistence.entity.PurchaseOrderLineEntity;
import com.allen.purchase.infrastructure.persistence.jpaRepository.PurchaseOrderLineRepos;
import com.allen.purchase.infrastructure.persistence.mappers.PurchaseOrderMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseOrderLineImpl implements PurchaseOrderLineReposPort {

    private final PurchaseOrderLineRepos repos;
    private final PurchaseOrderMapper mapper;

    public PurchaseOrderLineImpl(PurchaseOrderLineRepos repos, PurchaseOrderMapper mapper) {
        this.repos = repos;
        this.mapper = mapper;
    }

    @Override
    public PurchaseOrderLine create(PurchaseOrderLine orderLine) {
        if (orderLine == null) {
            throw new IllegalArgumentException("PurchaseOrderLine cannot be null");
        }
        PurchaseOrderLineEntity entity = mapper.purchaseOrderLineToEntity(orderLine);
        PurchaseOrderLineEntity saved = repos.save(entity);
        return mapper.purchaseOrderLineEntityToDomain(saved);
    }

    @Override
    public PurchaseOrderLine update(PurchaseOrderLine orderLine, Long id) {
        PurchaseOrderLineEntity existingEntity = repos.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PurchaseOrderLine not found with id: " + id));

        // Update mutable fields
        existingEntity.setProductId(orderLine.productId());
        existingEntity.setWarehouseId(orderLine.warehouseId());
        existingEntity.setQuantityOrdered(orderLine.quantityOrdered());
        existingEntity.setUnitPrice(orderLine.unitPrice());
        existingEntity.setTotalPrice(orderLine.quantityOrdered() * orderLine.unitPrice());

        PurchaseOrderLineEntity updated = repos.save(existingEntity);
        return mapper.purchaseOrderLineEntityToDomain(updated);
    }

    @Override
    public List<PurchaseOrderLine> findAll() {
        return repos.findAll()
                .stream()
                .map(mapper::purchaseOrderLineEntityToDomain)
                .toList();
    }

    @Override
    public Optional<PurchaseOrderLine> findByIdPurchaseOrderLine(Long id) {
        return repos.findById(id)
                .map(mapper::purchaseOrderLineEntityToDomain);
    }

    @Override
    public void deletPurchaseOrderLine(Long id) {
        if (!repos.existsById(id)) {
            throw new IllegalArgumentException("PurchaseOrderLine not found with id: " + id);
        }
        repos.deleteById(id);
    }
}


