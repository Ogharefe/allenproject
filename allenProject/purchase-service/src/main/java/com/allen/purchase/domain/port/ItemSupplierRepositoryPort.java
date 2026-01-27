package com.allen.purchase.domain.port;

import com.allen.purchase.domain.model.ItemSupplier;

import java.util.List;
import java.util.Optional;

public interface ItemSupplierRepositoryPort {
    ItemSupplier saveSupplier(ItemSupplier supplier);
    Optional<ItemSupplier> findSupplierById(Long id);
    List<ItemSupplier> findAllSuppliers();
    void deleteById(Long id);
}
