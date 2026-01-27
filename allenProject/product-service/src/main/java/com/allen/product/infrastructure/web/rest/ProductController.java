package com.allen.product.infrastructure.web.rest;

import com.allen.product.application.applicationMapper.AppProductMapper;
import com.allen.product.application.dto.ProductDto;
import com.allen.product.application.usecase.ProductUseCase;
import com.allen.product.domain.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductUseCase productUseCase;
    private final AppProductMapper productMapper;

    public ProductController(ProductUseCase productUseCase, AppProductMapper productMapper) {
        this.productUseCase = productUseCase;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        List<ProductDto> productDtos = productUseCase.getAllProducts()
                .stream().map(productMapper::productToProductDto)
                .toList();
        if(productDtos.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(productDtos);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productUseCase.getProductById(id)
                .map(product -> ResponseEntity.ok(productMapper.productToProductDto(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {

        Product product = productMapper.productDtoToProduct(productDto);
        Product createdProduct = productUseCase.createProduct(product);

        if (createdProduct != null) {
            ProductDto responseDto = productMapper.productToProductDto(createdProduct);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

     @PutMapping("/{productId}")
      ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto dto){

         Product product = productMapper.productDtoToProduct(dto);
         Product updatedProduct = productUseCase.updateProduct(productId,product);
         ProductDto productDto = productMapper.productToProductDto(updatedProduct);
         return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}