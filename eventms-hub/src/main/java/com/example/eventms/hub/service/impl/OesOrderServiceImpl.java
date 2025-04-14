package com.example.eventms.hub.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.common.exception.Asserts;
import com.example.eventms.hub.dto.OrderCompletion;
import com.example.eventms.hub.dto.OrderParam;
import com.example.eventms.hub.dto.OrderResult;
import com.example.eventms.hub.mapper.OrderConverter;
import com.example.eventms.hub.service.IOesOrderService;
import com.example.eventms.mbp.domain.TicketDetail;
import com.example.eventms.mbp.entity.OesOrder;
import com.example.eventms.mbp.entity.OesOrderAttendee;
import com.example.eventms.mbp.mapper.EesTicketMapper;
import com.example.eventms.mbp.mapper.EesTicketStockMapper;
import com.example.eventms.mbp.mapper.OesOrderAttendeeMapper;
import com.example.eventms.mbp.mapper.OesOrderMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import static com.example.eventms.common.constant.BusinessConstants.*;
import static com.example.eventms.hub.dto.OrderParam.TicketDto;
import static java.util.stream.Collectors.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class OesOrderServiceImpl extends ServiceImpl<OesOrderMapper, OesOrder> implements IOesOrderService {
    EesTicketMapper ticketMapper;
    EesTicketStockMapper ticketStockMapper;
    OesOrderAttendeeMapper orderAttendeeMapper;
    OrderConverter orderConverter;

    @Override
    public OrderResult generateOrder(OrderParam orderParam) {
        // prepare
        Long eventId = orderParam.getEventId();
        List<TicketDto> ticketDtos = orderParam.getTickets();

        // grouping
        List<Long> ticketIds = ticketDtos.stream().map(TicketDto::getTicketId)
                .distinct().toList();
        Map<Long, Integer> tQuantityMap = ticketDtos.stream()
                .collect(toMap(TicketDto::getTicketId, TicketDto::getQuantity));

        List<TicketDetail> ticketDetails = ticketMapper.getTicketDetail(ticketIds);

        // remove fake ticket ids
        ticketDetails = ticketDetails.stream()
                .filter(t -> Objects.equals(eventId, t.getEventId()))
                .toList();

        if (isQuantityExceedingMaxPerOrder(ticketDetails, tQuantityMap)) {
            Asserts.fail("Quantity exceeds the maximum allowed per order");
        }

        if (!hasStock(ticketDetails, tQuantityMap)) {
            Asserts.fail("Insufficient stock, unable to place order");
        }

        List<OesOrderAttendee> orderAttendees = new ArrayList<>();
        for (TicketDetail t : ticketDetails) {
            int quantity = tQuantityMap.get(t.getId());
            // one attendee per quantity
            for (int i = 0; i < quantity; i++) {
                OesOrderAttendee a = new OesOrderAttendee();
                a.setTicketId(t.getId());
                a.setTicketName(t.getName());
                a.setTicketPic(t.getPic());
                a.setTicketPrice(t.getPrice());
                // mock
                a.setTicketQuantity(1);
                a.setTicketStockId(t.getStock().getId());
                a.setBillReceiverEmail("admin@test.com");
                a.setReceiverName("admin");
                // end
                orderAttendees.add(a);
            }
        }

        if (orderParam.getCouponId() == null) {
            for (OesOrderAttendee a : orderAttendees)
                a.setCouponAmount(new BigDecimal(0L));
        } else assert true;

        calRealAmount(orderAttendees);

        reserveTickets(ticketDetails, tQuantityMap);

        OesOrder order = new OesOrder();
        // mock
        order.setUserId(1L);
        order.setEventId(eventId);
        order.setOrderSn(null);
        order.setOwnerName("admin");
        order.setTotalAmount(calcTotalAmount(orderAttendees));
        if (orderParam.getCouponId() == null) {
            order.setCouponAmount(new BigDecimal(0));
        } else {
            order.setCouponId(orderParam.getCouponId());
            // calc coupon
        }
        order.setCheckoutMethod(DEFAULT_ORDER_CHECKOUT_METHOD);
        order.setCheckoutMethod(DEFAULT_ORDER_DELIVERY_METHOD);
        order.setStatus(DEFAULT_ORDER_STATUS);
        order.setBillReceiverEmail("admin@test.com");
        order.setReceiverName("admin");

        save(order);

        for (OesOrderAttendee a : orderAttendees) {
            a.setOrderId(order.getId());
            a.setOrderSn(null);
        }
        orderAttendeeMapper.insert(orderAttendees);

        if (orderParam.getCouponId() != null) {
            // update coupon
            assert true;
        } else System.out.println("pass");

        return orderConverter.toOrderResult(order, orderAttendees);
    }

    @Override
    public Integer paySuccess(Long orderId, OrderCompletion payload) {
        OesOrder order = new OesOrder();
        order.setStatus(DEFAULT_ORDER_COMPLETION_STATUS);
        order.setDeliveryMethod(payload.getDeliveryMethod());
        order.setPaymentTime(LocalDateTime.now());

        var orderWrp = new LambdaUpdateWrapper<OesOrder>();
        orderWrp.eq(OesOrder::getId, orderId).eq(OesOrder::getStatus, DEFAULT_ORDER_STATUS);
        boolean updated = update(order, orderWrp);

        if (!updated) Asserts.fail("Order not found or has an unexpected status");


        List<OesOrderAttendee> orderAttendees = getOrderAttendeesByOrderId(orderId);

        Map<Long, Long> stockIdToReservedQtyMap = orderAttendees.stream()
                .collect(groupingBy(OesOrderAttendee::getTicketStockId, counting()));

        int totalCount = 0;
        for (Entry<Long, Long> e : stockIdToReservedQtyMap.entrySet()) {
            Long stockId = e.getKey();
            Long reversedQty = e.getValue();
            int count = ticketStockMapper.commitLockedStockById(stockId, reversedQty);
            if (count == 0) Asserts.fail("Insufficient inventory");
            totalCount += count;
        }

        return totalCount;
    }

    @Override
    public void abandonOrder(Long orderId) {
        var orderWrp = new LambdaUpdateWrapper<OesOrder>();
        orderWrp.eq(OesOrder::getId, orderId).eq(OesOrder::getStatus, DEFAULT_ORDER_STATUS);
        OesOrder abandonOrder = getOne(orderWrp);

        if (abandonOrder == null) Asserts.fail("Order not found or has an unexpected status");
        abandonOrder.setStatus(DEFAULT_ORDER_ABANDON_STATUS);
        updateById(abandonOrder);

        List<OesOrderAttendee> orderAttendees = getOrderAttendeesByOrderId(orderId);

        Map<Long, Long> stockIdToReservedQtyMap = orderAttendees.stream()
                .collect(groupingBy(OesOrderAttendee::getTicketStockId, counting()));

        for (Entry<Long, Long> e : stockIdToReservedQtyMap.entrySet()) {
            Long stockId = e.getKey();
            Long reversedQty = e.getValue();
            int count = ticketStockMapper.releaseLockedStockById(stockId, reversedQty);
            if (count == 0) Asserts.fail("Insufficient inventory");
        }

        if (abandonOrder.getCouponId() != null) {
            // handle
            assert true;
        } else System.out.println("pass");
    }

    private List<OesOrderAttendee> getOrderAttendeesByOrderId(Long orderId) {
        var orderAttendeeWrp = new LambdaUpdateWrapper<OesOrderAttendee>();
        orderAttendeeWrp.eq(OesOrderAttendee::getOrderId, orderId);
        return orderAttendeeMapper.selectList(orderAttendeeWrp);
    }

    private boolean isQuantityExceedingMaxPerOrder(List<TicketDetail> ts, Map<Long, Integer> tQuantityMap) {
        for (TicketDetail t : ts) {
            Integer quantity = tQuantityMap.get(t.getId());
            Integer maxQuantityPerOrder = t.getMaxQuantityPerOrder();
            if (quantity > maxQuantityPerOrder) return true;
        }
        return false;
    }

    private boolean hasStock(List<TicketDetail> ts, Map<Long, Integer> tQuantityMap) {
        for (TicketDetail t : ts) {
            int realQuantity = t.getStock().getQuantityAvailable();
            if (realQuantity <= 0 || realQuantity < tQuantityMap.get(t.getId())) return false;
        }
        return true;
    }

    private void calRealAmount(List<OesOrderAttendee> orderAttendees) {
        for (OesOrderAttendee a : orderAttendees) {
            BigDecimal realAmount = a.getTicketPrice().subtract(a.getCouponAmount());
            a.setRealAmount(realAmount);
        }
    }

    private void reserveTickets(List<TicketDetail> ts, Map<Long, Integer> tQuantityMap) {
        for (TicketDetail t : ts) {
            int count = ticketStockMapper.lockStockById(
                    t.getStock().getId(),
                    tQuantityMap.get(t.getId())
            );
            if (count == 0) Asserts.fail("Insufficient stock, unable to place order");
        }
    }

    private BigDecimal calcTotalAmount(List<OesOrderAttendee> orderAttendees) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OesOrderAttendee a : orderAttendees) totalAmount = totalAmount.add(a.getTicketPrice());
        return totalAmount;
    }
}
