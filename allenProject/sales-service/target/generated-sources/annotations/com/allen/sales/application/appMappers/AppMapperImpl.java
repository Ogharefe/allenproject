package com.allen.sales.application.appMappers;

import com.allen.event_contracts.enums.PaymentMethod;
import com.allen.event_contracts.enums.SaleStatus;
import com.allen.sales.application.requestDataDto.PaymentRequesttDto;
import com.allen.sales.application.requestDataDto.SaleItemRequestDto;
import com.allen.sales.application.requestDataDto.SaleRequestDto;
import com.allen.sales.domain.model.Payment;
import com.allen.sales.domain.model.Sale;
import com.allen.sales.domain.model.SaleItem;
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
public class AppMapperImpl implements AppMapper {

    @Override
    public Sale toDomain(SaleRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UUID idSale = null;
        LocalDateTime date = null;
        UUID customerId = null;
        List<SaleItem> items = null;
        BigDecimal totalAmount = null;
        SaleStatus status = null;

        idSale = dto.idSale();
        date = dto.date();
        customerId = dto.customerId();
        items = saleItemRequestDtoListToSaleItemList( dto.items() );
        totalAmount = dto.totalAmount();
        status = dto.status();

        Payment payment = null;

        Sale sale = new Sale( idSale, date, customerId, items, totalAmount, status, payment );

        return sale;
    }

    @Override
    public SaleRequestDto toDto(Sale domain) {
        if ( domain == null ) {
            return null;
        }

        UUID idSale = null;
        LocalDateTime date = null;
        UUID customerId = null;
        BigDecimal totalAmount = null;
        SaleStatus status = null;
        List<SaleItemRequestDto> items = null;

        idSale = domain.idSale();
        date = domain.date();
        customerId = domain.customerId();
        totalAmount = domain.totalAmount();
        status = domain.status();
        items = saleItemListToSaleItemRequestDtoList( domain.items() );

        SaleRequestDto saleRequestDto = new SaleRequestDto( idSale, date, customerId, totalAmount, status, items );

        return saleRequestDto;
    }

    @Override
    public SaleItem toDomain(SaleItemRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UUID productId = null;
        String productName = null;
        BigDecimal unitPrice = null;
        int quantity = 0;

        productId = dto.productId();
        productName = dto.productName();
        unitPrice = dto.unitPrice();
        quantity = dto.quantity();

        UUID idSaleItem = UUID.randomUUID();

        SaleItem saleItem = new SaleItem( idSaleItem, productId, productName, unitPrice, quantity );

        return saleItem;
    }

    @Override
    public SaleItemRequestDto toDto(SaleItem domain) {
        if ( domain == null ) {
            return null;
        }

        UUID productId = null;
        String productName = null;
        BigDecimal unitPrice = null;
        int quantity = 0;

        productId = domain.productId();
        productName = domain.productName();
        unitPrice = domain.unitPrice();
        quantity = domain.quantity();

        String sku = null;
        String description = null;

        SaleItemRequestDto saleItemRequestDto = new SaleItemRequestDto( productId, productName, unitPrice, sku, quantity, description );

        return saleItemRequestDto;
    }

    @Override
    public Payment toDomain(PaymentRequesttDto dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentMethod method = null;
        BigDecimal amount = null;
        LocalDateTime paymentDate = null;

        method = dto.method();
        amount = dto.amount();
        paymentDate = dto.paymentDate();

        Payment payment = new Payment( method, amount, paymentDate );

        return payment;
    }

    @Override
    public PaymentRequesttDto toDto(Payment domain) {
        if ( domain == null ) {
            return null;
        }

        PaymentMethod method = null;
        BigDecimal amount = null;
        LocalDateTime paymentDate = null;

        method = domain.method();
        amount = domain.amount();
        paymentDate = domain.paymentDate();

        PaymentRequesttDto paymentRequesttDto = new PaymentRequesttDto( method, amount, paymentDate );

        return paymentRequesttDto;
    }

    protected List<SaleItem> saleItemRequestDtoListToSaleItemList(List<SaleItemRequestDto> list) {
        if ( list == null ) {
            return null;
        }

        List<SaleItem> list1 = new ArrayList<SaleItem>( list.size() );
        for ( SaleItemRequestDto saleItemRequestDto : list ) {
            list1.add( toDomain( saleItemRequestDto ) );
        }

        return list1;
    }

    protected List<SaleItemRequestDto> saleItemListToSaleItemRequestDtoList(List<SaleItem> list) {
        if ( list == null ) {
            return null;
        }

        List<SaleItemRequestDto> list1 = new ArrayList<SaleItemRequestDto>( list.size() );
        for ( SaleItem saleItem : list ) {
            list1.add( toDto( saleItem ) );
        }

        return list1;
    }
}
