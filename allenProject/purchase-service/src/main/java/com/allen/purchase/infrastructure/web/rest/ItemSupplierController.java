package com.allen.purchase.infrastructure.web.rest;

import com.allen.purchase.applications.appMappers.AppItemSupplierMapper;
import com.allen.purchase.applications.dtos.ItemSupplierDTO;
import com.allen.purchase.applications.usecase.ItemSupplierUseCase;
import com.allen.purchase.domain.model.ItemSupplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/suppliers")
@RestController
public class ItemSupplierController {

    private final ItemSupplierUseCase useCase;
    private final AppItemSupplierMapper mapper;

    public ItemSupplierController(ItemSupplierUseCase useCase, AppItemSupplierMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ItemSupplierDTO> saveSupplier(@RequestBody ItemSupplierDTO dto){

        ItemSupplier domain = mapper.fromDtoToItemSupplierDomain(dto);
        ItemSupplier created = useCase.createSupplier(domain);
        if(created != null) {
            ItemSupplierDTO supplierDTO = mapper.fromDomainToItemSupplierDTO(created);
            return new ResponseEntity<>(supplierDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<ItemSupplierDTO>> findAllSuppliers(){
        List<ItemSupplierDTO> dtos = useCase.findAllSuppliers()
                .stream().map(domain -> mapper.fromDomainToItemSupplierDTO(domain))
                .toList();
        if(dtos.isEmpty() || dtos == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletSupplier(@PathVariable Long id){
        useCase.deletSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
