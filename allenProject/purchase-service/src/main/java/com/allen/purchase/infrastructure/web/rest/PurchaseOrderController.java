package com.allen.purchase.infrastructure.web.rest;

import com.allen.purchase.applications.appMappers.AppPurchaseOrderMapper;
import com.allen.purchase.applications.dtos.PurchaseOrderDTO;
import com.allen.purchase.applications.usecase.PurchaseOrderUseCase;
import com.allen.purchase.domain.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/purchaseorders")
@RestController
public class PurchaseOrderController {

    private final PurchaseOrderUseCase orderUseCase;
    private final AppPurchaseOrderMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderController.class);

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


    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> listPurchaseOrder(){

        List<PurchaseOrder> orders = orderUseCase.listOrders();
        log.info("Retrieved {} stocks", orders.size());
        List<PurchaseOrderDTO> orderDTOS = orders.stream()
                .map(mapper::purchaseOrderToDTO)
                .toList();

        return ResponseEntity.ok(orderDTOS);
    }
}
