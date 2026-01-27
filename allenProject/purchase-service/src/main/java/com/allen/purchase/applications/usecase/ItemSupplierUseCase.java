package com.allen.purchase.applications.usecase;

import com.allen.purchase.domain.model.ItemSupplier;
import com.allen.purchase.domain.model.PurchaseOrder;

import java.util.List;

public interface ItemSupplierUseCase {

    ItemSupplier createSupplier(ItemSupplier domain);
    ItemSupplier findBySupplierId(Long id);
    List<ItemSupplier> findAllSuppliers();
    void deletSupplier(Long id);
}
