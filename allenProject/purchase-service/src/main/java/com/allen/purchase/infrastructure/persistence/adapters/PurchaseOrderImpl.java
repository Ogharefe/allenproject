package com.allen.purchase.infrastructure.persistence.adapters;

import com.allen.purchase.domain.model.PurchaseOrder;
import com.allen.purchase.domain.port.PurchaseOrderRepositoryPort;
import com.allen.purchase.infrastructure.persistence.entity.PurchaseOrderEntity;
import com.allen.purchase.infrastructure.persistence.jpaRepository.PurchaseOrderRepository;
import com.allen.purchase.infrastructure.persistence.mappers.PurchaseOrderMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PurchaseOrderImpl implements PurchaseOrderRepositoryPort {

    private final PurchaseOrderRepository repository;
    private final PurchaseOrderMapper mapper;

    public PurchaseOrderImpl(PurchaseOrderRepository repository, PurchaseOrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PurchaseOrder create(PurchaseOrder order) {

        PurchaseOrderEntity entity = mapper.toEntityWithRelations(order);
        return mapper.purchaseEntityToDomain(repository.save(entity));
    }

    @Override
    public Optional<PurchaseOrder> findByPurchaseOrderId(Long id) {
        return repository.findById(id)
                .map(mapper::purchaseEntityToDomain);
    }

    @Override
    public List<PurchaseOrder> findAllPurchaseOrder() {

        List<PurchaseOrderEntity>orderEntities = repository.findAll();
        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new RuntimeException("No order found in the database.");
        }

        return orderEntities.stream().map(mapper::purchaseEntityToDomain)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteById(Long id) {

        PurchaseOrderEntity order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        repository.delete(order);
    }
}
