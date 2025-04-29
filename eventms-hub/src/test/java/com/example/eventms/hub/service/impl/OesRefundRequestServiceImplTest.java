package com.example.eventms.hub.service.impl;

import com.example.eventms.common.exception.AppException;
import com.example.eventms.hub.dto.RefundRequestPayload;
import com.example.eventms.hub.mapper.OrderConverter;
import com.example.eventms.mbp.entity.OesOrder;
import com.example.eventms.mbp.entity.OesRefundRequest;
import com.example.eventms.mbp.mapper.OesOrderMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.eventms.common.constant.BusinessConstants.DEFAULT_ORDER_COMPLETION_STATUS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
public class OesRefundRequestServiceImplTest {
    @Spy
    @InjectMocks
    OesRefundRequestServiceImpl refundService;

    @Mock
    OesOrderMapper orderMapper;

    @Mock
    OrderConverter orderConverter;

    RefundRequestPayload refundReqPayload;

    OesRefundRequest refundReq;

    OesOrder order;

    static final Long ORDER_ID = 1L;

    static final Integer WRONG_ORDER_COMPLETION_STATUS = -1;

    @BeforeEach
    public void setup() {
        refundReqPayload = new RefundRequestPayload();
        refundReqPayload.setReason("Test reason");

        refundReq = new OesRefundRequest();
        refundReq.setReason("Test reason");
        refundReq.setOrderId(ORDER_ID);

        order = new OesOrder();
        order.setId(ORDER_ID);
        order.setStatus(DEFAULT_ORDER_COMPLETION_STATUS);
    }

    @Test
    public void requestRefund_orderNotFound() {
        when(orderConverter.toEntity(refundReqPayload)).thenReturn(refundReq);
        // force to else branch to run
        when(orderMapper.selectOne(any())).thenReturn(null);

        // expect
        var exception = assertThrows(AppException.class,
                () -> refundService.requestRefund(ORDER_ID, refundReqPayload));
        assertEquals("Order not found", exception.getMessage());
        verify(orderConverter).toEntity(refundReqPayload);
        verify(orderMapper).selectOne(any());
        verify(refundService, never()).save(any());
    }

    @Test
    public void requestRefund_success() {
        // arr
        when(orderConverter.toEntity(refundReqPayload)).thenReturn(refundReq);
        when(orderMapper.selectOne(any())).thenReturn(order);
        doReturn(true).when(refundService).save(refundReq);

        // act
        boolean result = refundService.requestRefund(ORDER_ID, refundReqPayload);

        // expect
        assertTrue(result);
        verify(orderConverter).toEntity(refundReqPayload);
        verify(orderMapper).selectOne(any());
        verify(refundService).save(refundReq);
    }

    @Test
    public void requestRefund_invalidOrderStatus() {
        order.setStatus(WRONG_ORDER_COMPLETION_STATUS);

        when(orderConverter.toEntity(refundReqPayload)).thenReturn(refundReq);
        when(orderMapper.selectOne(any())).thenReturn(order);

        var exception = assertThrows(AppException.class,
                () -> refundService.requestRefund(ORDER_ID, refundReqPayload));
        assertEquals("Order has an unexpected status", exception.getMessage());
        verify(orderConverter).toEntity(refundReqPayload);
        verify(orderMapper).selectOne(any());
        verify(refundService, never()).save(any());
    }
}
