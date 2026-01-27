package com.allen.product.infrastructure.persistence.entity;

import com.allen.event_contracts.enums.StockUpdateType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transaction")
//@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Getter @Setter
public class StockTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockTransactionId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockUpdateType type;

    @Column(name = "quantity_change", nullable = false)
    private Integer quantityChange;

    @Column(name = "resulting_quantity", nullable = false)
    private Integer resultingQuantity;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    public Long getStockTransactionId() {
        return stockTransactionId;
    }

    public void setStockTransactionId(Long stockTransactionId) {
        this.stockTransactionId = stockTransactionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public StockUpdateType getType() {
        return type;
    }

    public void setType(StockUpdateType type) {
        this.type = type;
    }

    public Integer getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(Integer quantityChange) {
        this.quantityChange = quantityChange;
    }

    public Integer getResultingQuantity() {
        return resultingQuantity;
    }

    public void setResultingQuantity(Integer resultingQuantity) {
        this.resultingQuantity = resultingQuantity;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}