package com.example.eventms.hub.service.impl;

import com.example.eventms.common.exception.AppException;
import com.example.eventms.hub.component.CancelOrderSender;
import com.example.eventms.hub.dto.OrderParam;
import com.example.eventms.hub.mapper.OrderConverter;
import com.example.eventms.mbp.domain.TicketDetail;
import com.example.eventms.mbp.entity.OesOrder;
import com.example.eventms.mbp.entity.OesOrderAttendee;
import com.example.eventms.mbp.mapper.EesTicketMapper;
import com.example.eventms.mbp.mapper.EesTicketStockMapper;
import com.example.eventms.mbp.mapper.OesOrderAttendeeMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static com.example.eventms.hub.dto.OrderParam.TicketDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class OesOrderServiceImplTest {
    @Mock
    EesTicketMapper ticketMapper;
    @Mock
    EesTicketStockMapper ticketStockMapper;
    @Mock
    OesOrderAttendeeMapper oesOrderAttendeeMapper;
    @Mock
    OrderConverter orderConverter;
    @Mock
    CancelOrderSender cancelOrderSender;

    OesOrderServiceImpl orderService;

    static OesOrder discountedOrder;
    static OesOrder nonDiscountedOrder;

    @BeforeAll
    static void beforeAll() {
        discountedOrder = new OesOrder();
        discountedOrder.setId(1L);
        discountedOrder.setUserId(1L);
        discountedOrder.setEventId(1L);
        discountedOrder.setCouponId(1L);
        discountedOrder.setOwnerName("owner name");
        nonDiscountedOrder = new OesOrder();
    }

    @BeforeEach
    void setUp() {
        orderService = new OesOrderServiceImpl(
                ticketMapper,
                ticketStockMapper,
                oesOrderAttendeeMapper,
                orderConverter,
                cancelOrderSender
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateOrder_WithBadParams_ShouldThrowException() {
        var ticketParam = Map.of(1L, 3, 2L, 5, 3L, 7);
        var orderParam = mockOrderParam(100L, ticketParam);
        when(ticketMapper.getTicketDetail(any())).thenReturn(Arrays.asList(
                mockTicketDetailWithEIdIs1(), // != 100
                mockTicketDetailWithEIdIs1(),
                mockTicketDetailWithEIdIs1()
        ));

        var except = assertThrows(AppException.class, () -> orderService.generateOrder(orderParam));

        assertEquals("No valid tickets to process", except.getMessage());
    }

    @Test
    void generateOrder_WithQtyExceedsMaxPerOrder_ShouldThrowException() {
        var ticketParam = Map.of(1L, 3, 2L, 5, 3L, 7);
        var orderParam = mockOrderParam(1L, ticketParam);
        var ticketDetails = Arrays.asList(
                mockTicketDetail(1L, 5), // max > qty
                mockTicketDetail(2L, 5), // max = qty
                mockTicketDetail(3L, 5) // max < qty
        );
        var spyService = Mockito.spy(orderService);
        doReturn(ticketDetails).when(spyService).filterTicketsByEvent(anyList(), anyLong());

        var except = assertThrows(AppException.class, () -> spyService.generateOrder(orderParam));

        assertEquals("Quantity exceeds the maximum allowed per order", except.getMessage());
        verify(spyService).filterTicketsByEvent(anyList(), eq(1L));
    }

    @Test
    public void isQuantityExceedingMaxPerOrder_ShouldCorrectly() {
        var ticketDetails = Arrays.asList(
                mockTicketDetail(1L, 3),
                mockTicketDetail(2L, 5)
        );
        var qtyMap = Map.of(1L, 5, 2L, 3);
        var validQtyMap = Map.of(1L, 2, 2L, 3);

        var result = orderService.isQuantityExceedingMaxPerOrder(ticketDetails, qtyMap);
        assertTrue(result);

        boolean validResult = orderService.isQuantityExceedingMaxPerOrder(ticketDetails, validQtyMap);
        assertFalse(validResult);
    }

    @Test
    void calcTotalAmount_WithEmptyList_ShouldReturnZero() {
        var emptyList = new ArrayList<OesOrderAttendee>();
        var result = orderService.calcTotalAmount(emptyList);
        assertEquals(new BigDecimal("0"), result);
    }

    @Test
    void calcTotalAmount_WithLargePrices_ShouldHandleCorrectly() {
        var attendee1 = mock(OesOrderAttendee.class);
        var attendee2 = mock(OesOrderAttendee.class);
        var attendees = Arrays.asList(attendee1, attendee2);
        when(attendee1.getTicketPrice()).thenReturn(new BigDecimal("999999.99"));
        when(attendee2.getTicketPrice()).thenReturn(new BigDecimal("1000000.01"));

        var result = orderService.calcTotalAmount(attendees);

        assertEquals(new BigDecimal("2000000.00"), result);
    }

    @Test
    void filterTicketsByEvent_WithEmptyTicketIds_ShouldReturnEmptyList() {
        var emptyTicketIds = new ArrayList<Long>();
        var eventId = 1L;
        when(ticketMapper.getTicketDetail(emptyTicketIds)).thenReturn(new ArrayList<>());

        var result = orderService.filterTicketsByEvent(emptyTicketIds, eventId);

        assertTrue(result.isEmpty());
        verify(ticketMapper).getTicketDetail(emptyTicketIds);
    }

    @Test
    void filterTicketsByEvent_WithNoMatchingEventId_ShouldReturnEmptyList() {
        var ticketIds = Arrays.asList(1L, 2L, 3L);
        var eventId = 100L;
        when(ticketMapper.getTicketDetail(ticketIds)).thenReturn(Arrays.asList(
                mockTicketDetailWithEIdIs1(), // != 100
                mockTicketDetailWithEIdIs1(),
                mockTicketDetailWithEIdIs1()
        ));

        var result = orderService.filterTicketsByEvent(ticketIds, eventId);

        assertTrue(result.isEmpty());
        verify(ticketMapper).getTicketDetail(ticketIds);
    }

    @Test
    void filterTicketsByEvent_WithAllTicketsMatchingEventId_ShouldReturnAllTickets() {
        var ticketIds = Arrays.asList(1L, 2L, 3L);
        var eventId = 1L;
        when(ticketMapper.getTicketDetail(ticketIds)).thenReturn(Arrays.asList(
                mockTicketDetailWithEIdIs1(), // != 100
                mockTicketDetailWithEIdIs1(),
                mockTicketDetailWithEIdIs1()
        ));

        var result = orderService.filterTicketsByEvent(ticketIds, eventId);

        assertEquals(3, result.size());
        verify(ticketMapper).getTicketDetail(ticketIds);
    }

    @Test
    void filterTicketsByEvent_WithSomeTicketsMatchingEventId_ShouldReturnOnlyMatchingTickets() {
        var ticketIds = Arrays.asList(1L, 2L, 3L, 4L);
        var eventId = 10L;
        var ticket1 = mock(TicketDetail.class);
        var ticket2 = mock(TicketDetail.class);
        var ticket3 = mock(TicketDetail.class);
        var ticket4 = mock(TicketDetail.class);
        var allTickets = Arrays.asList(ticket1, ticket2, ticket3, ticket4);
        when(ticket1.getEventId()).thenReturn(10L); // match
        when(ticket2.getEventId()).thenReturn(20L); // no match
        when(ticket3.getEventId()).thenReturn(10L); // match
        when(ticket4.getEventId()).thenReturn(30L); // no match
        when(ticketMapper.getTicketDetail(ticketIds)).thenReturn(allTickets);

        var result = orderService.filterTicketsByEvent(ticketIds, eventId);

        assertEquals(2, result.size());
        assertTrue(result.contains(ticket1));
        assertTrue(result.contains(ticket3));
        assertFalse(result.contains(ticket2));
        assertFalse(result.contains(ticket4));
        verify(ticketMapper).getTicketDetail(ticketIds);
    }

    private static OrderParam mockOrderParam(Long eId, Map<Long, Integer> ticketParam) {
        var op = new OrderParam();
        op.setEventId(eId);
        var list = new ArrayList<TicketDto>();
        for (var entry : ticketParam.entrySet()) {
            var td = mockTicketDto(entry.getKey(), entry.getValue());
            list.add(td);
        }
        op.setTickets(list);
        return op;
    }

    private static TicketDto mockTicketDto(Long tId, Integer qty) {
        var td = new TicketDto();
        td.setTicketId(tId);
        td.setQuantity(qty);
        return td;
    }

    private static TicketDetail mockTicketDetail(Long tId, Integer maxQuantityPerOrder) {
        var td = new TicketDetail();
        td.setId(tId);
        td.setMaxQuantityPerOrder(maxQuantityPerOrder);
        return td;
    }

    private static TicketDetail mockTicketDetailWithEIdIs1() {
        var td = new TicketDetail();
        td.setEventId(1L);
        return td;
    }
}
