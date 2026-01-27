package com.allen.product.infrastructure.web.rest;

import com.allen.product.application.applicationMapper.AppWarehouseMapper;
import com.allen.product.application.dto.WarehouseDTO;
import com.allen.product.application.usecase.WarehouseUseCase;
import com.allen.product.domain.model.Warehouse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseUseCase warehouseUseCase;
    private final AppWarehouseMapper mapper;

    public WarehouseController(WarehouseUseCase warehouseUseCase, AppWarehouseMapper mapper) {
        this.warehouseUseCase = warehouseUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {

        Warehouse warehouse = mapper.warehouseDtoToWarehouseDomain(warehouseDTO);
        Warehouse createdWH = warehouseUseCase.createWarehouse(warehouse);

        if (createdWH != null) {
            WarehouseDTO dto = mapper.warehousDomainToWarehouseDTO(createdWH);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> findAllWarehouse(){

        List<WarehouseDTO> warehouseDTOList = warehouseUseCase.getWarehouses()
            .stream().map(warehouse -> mapper.warehousDomainToWarehouseDTO(warehouse))
                .toList();
        return new ResponseEntity<>(warehouseDTOList,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        warehouseUseCase.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }
}
