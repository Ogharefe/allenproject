package com.allen.sales.infrastructure.persistence.mapper;

import com.allen.event_contracts.enums.PaymentMethod;
import com.allen.event_contracts.enums.SaleStatus;
import com.allen.sales.domain.model.Payment;
import com.allen.sales.domain.model.Sale;
import com.allen.sales.domain.model.SaleItem;
import com.allen.sales.infrastructure.persistence.entity.PaymentEntity;
import com.allen.sales.infrastructure.persistence.entity.SaleEntity;
import com.allen.sales.infrastructure.persistence.entity.SaleItemEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-27T05:58:58+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (OpenLogic)"
)
@Component
public class SaleMapperImpl implements SaleMapper {

    @Override
    public SaleEntity toEntity(Sale domain) {
        if ( domain == null ) {
            return null;
        }

        SaleEntity saleEntity = new SaleEntity();

        saleEntity.setItems( saleItemListToSaleItemEntityList( domain.items() ) );
        saleEntity.setPayment( paymentToPaymentEntity( domain.payment() ) );
        saleEntity.setIdSale( domain.idSale() );
        saleEntity.setCustomerId( domain.customerId() );
        saleEntity.setTotalAmount( domain.totalAmount() );
        saleEntity.setStatus( domain.status() );

        setBackReference( saleEntity );

        return saleEntity;
    }

    @Override
    public Sale toDomain(SaleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        List<SaleItem> items = null;
        UUID idSale = null;
        UUID customerId = null;
        BigDecimal totalAmount = null;
        SaleStatus status = null;
        Payment payment = null;

        items = saleItemEntityListToSaleItemList( entity.getItems() );
        idSale = entity.getIdSale();
        customerId = entity.getCustomerId();
        totalAmount = entity.getTotalAmount();
        status = entity.getStatus();
        payment = paymentEntityToPayment( entity.getPayment() );

        LocalDateTime date = null;

        Sale sale = new Sale( idSale, date, customerId, items, totalAmount, status, payment );

        return sale;
    }

    @Override
    public SaleItemEntity toEntity(SaleItem domain) {
        if ( domain == null ) {
            return null;
        }

        SaleItemEntity saleItemEntity = new SaleItemEntity();

        saleItemEntity.setProductId( domain.productId() );
        saleItemEntity.setProductName( domain.productName() );
        saleItemEntity.setQuantity( domain.quantity() );
        if ( domain.unitPrice() != null ) {
            saleItemEntity.setUnitPrice( domain.unitPrice().doubleValue() );
        }

        return saleItemEntity;
    }

    @Override
    public SaleItem toDomain(SaleItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID idSaleItem = null;
        UUID productId = null;
        String productName = null;
        BigDecimal unitPrice = null;
        int quantity = 0;

        idSaleItem = entity.getIdSaleItem();
        productId = entity.getProductId();
        productName = entity.getProductName();
        unitPrice = BigDecimal.valueOf( entity.getUnitPrice() );
        quantity = entity.getQuantity();

        SaleItem saleItem = new SaleItem( idSaleItem, productId, productName, unitPrice, quantity );

        return saleItem;
    }

    protected List<SaleItemEntity> saleItemListToSaleItemEntityList(List<SaleItem> list) {
        if ( list == null ) {
            return null;
        }

        List<SaleItemEntity> list1 = new ArrayList<SaleItemEntity>( list.size() );
        for ( SaleItem saleItem : list ) {
            list1.add( toEntity( saleItem ) );
        }

        return list1;
    }

    protected PaymentEntity paymentToPaymentEntity(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setMethod( payment.method() );
        paymentEntity.setAmount( payment.amount() );
        paymentEntity.setPaymentDate( payment.paymentDate() );

        return paymentEntity;
    }

    protected List<SaleItem> saleItemEntityListToSaleItemList(List<SaleItemEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<SaleItem> list1 = new ArrayList<SaleItem>( list.size() );
        for ( SaleItemEntity saleItemEntity : list ) {
            list1.add( toDomain( saleItemEntity ) );
        }

        return list1;
    }

    protected Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }

        PaymentMethod method = null;
        BigDecimal amount = null;
        LocalDateTime paymentDate = null;

        method = paymentEntity.getMethod();
        amount = paymentEntity.getAmount();
        paymentDate = paymentEntity.getPaymentDate();

        Payment payment = new Payment( method, amount, paymentDate );

        return payment;
    }
}
