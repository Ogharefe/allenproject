package com.allen.purchase.infrastructure.web.rest;

import com.allen.purchase.applications.appMappers.AppPurchaseOrderMapper;
import com.allen.purchase.applications.dtos.PurchaseOrderDTO;
import com.allen.purchase.applications.usecase.PurchaseOrderUseCase;
import com.allen.purchase.domain.model.PurchaseOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/purchaseorders")
@RestController
public class PurchaseOrderController {

    private final PurchaseOrderUseCase orderUseCase;
    private final AppPurchaseOrderMapper mapper;

    public PurchaseOrderController(PurchaseOrderUseCase orderUseCase, AppPurchaseOrderMapper mapper) {
        this.orderUseCase = orderUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> create(@RequestBody PurchaseOrderDTO orderDTO) {

        PurchaseOrder order = mapper.purchaseOrderDTOToDomain(orderDTO);
        PurchaseOrder created = orderUseCase.createPurchaseOrder(order);

        if(created != null){
            PurchaseOrderDTO dto = mapper.purchaseOrderToDTO(created);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
