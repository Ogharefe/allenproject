package com.allen.purchase.applications.service;

import com.allen.purchase.applications.usecase.ItemSupplierUseCase;
import com.allen.purchase.domain.model.ItemSupplier;
import com.allen.purchase.domain.port.ItemSupplierRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemSupplierService implements ItemSupplierUseCase {

    private final ItemSupplierRepositoryPort repository;

    public ItemSupplierService(ItemSupplierRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public ItemSupplier createSupplier(ItemSupplier domain) {
        return repository.saveSupplier(domain);
    }

    @Override
    public ItemSupplier findBySupplierId(Long id) {
        return repository.findSupplierById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @Override
    public List<ItemSupplier> findAllSuppliers() {
        return repository.findAllSuppliers();
    }

    @Override
    public void deletSupplier(Long id) {
        repository.deleteById(id);
    }
}