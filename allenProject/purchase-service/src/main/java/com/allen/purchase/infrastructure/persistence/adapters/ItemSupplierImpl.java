package com.allen.purchase.infrastructure.persistence.adapters;

import com.allen.purchase.domain.model.ItemSupplier;
import com.allen.purchase.domain.port.ItemSupplierRepositoryPort;
import com.allen.purchase.infrastructure.persistence.entity.ItemSupplierEntity;
import com.allen.purchase.infrastructure.persistence.jpaRepository.ItemSupplierRepository;
import com.allen.purchase.infrastructure.persistence.mappers.ItemSupplierMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ItemSupplierImpl implements ItemSupplierRepositoryPort {

    private final ItemSupplierRepository repository;
    private final ItemSupplierMapper mapper;

    public ItemSupplierImpl(ItemSupplierRepository repository, ItemSupplierMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ItemSupplier saveSupplier(ItemSupplier supplier) {

        ItemSupplierEntity entity = mapper.fromDomainToSupplierEntity(supplier);
        return mapper.fromEntityToItemSupplierDomain(repository.save(entity));
    }

    @Override
    public Optional<ItemSupplier> findSupplierById(Long id) {
        return repository.findById(id)
                .map(mapper::fromEntityToItemSupplierDomain);
    }

    @Override
    public List<ItemSupplier> findAllSuppliers() {

        List<ItemSupplierEntity>list = repository.findAll();
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("ItemSupplier list not found");
        }
        return list.stream().map(mapper::fromEntityToItemSupplierDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {

        ItemSupplierEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found !"));
        repository.delete(entity);
    }
}
