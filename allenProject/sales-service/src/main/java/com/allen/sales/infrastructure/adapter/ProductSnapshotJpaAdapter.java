package com.allen.sales.infrastructure.adapter;

import com.allen.sales.domain.model.ProductSnapshot;
import com.allen.sales.domain.port.ProductSnapshotPort;
import com.allen.sales.infrastructure.persistence.entity.ProductSnapshotEntity;
import com.allen.sales.infrastructure.persistence.japRepository.ProductSnapshotRepository;
import com.allen.sales.infrastructure.persistence.mapper.ProductSnapshotMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class ProductSnapshotJpaAdapter implements ProductSnapshotPort {

    private final ProductSnapshotRepository repository;
    private final ProductSnapshotMapper mapper;

    public ProductSnapshotJpaAdapter(ProductSnapshotRepository repository, ProductSnapshotMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void upsert(ProductSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "snapshot must not be null");
        Objects.requireNonNull(snapshot.productId(), "snapshot.productId must not be null");

        ProductSnapshotEntity entity = repository.findById(snapshot.productId())
                .map(existing -> {
                    mapper.updateEntity(snapshot, existing);
                    return existing;
                })
                .orElseGet(() -> mapper.toEntity(snapshot));

        repository.save(entity);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<ProductSnapshot> findBySku(String sku) {
        if (sku == null || sku.isBlank()) {
            return Optional.empty();
        }
        return repository.findBySku(sku).map(mapper::toDomain);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<ProductSnapshot> findByProductId(UUID productId) {
        if (productId == null) {
            return Optional.empty();
        }
        return repository.findById(productId).map(mapper::toDomain);
    }

    @Override
    public void deleteProductSnapshot(UUID id) {
        if (id == null) {
            return;
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ProductSnapshot> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}