package com.example.eventms.hub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.common.exception.Asserts;
import com.example.eventms.hub.dto.RefundApplyPayload;
import com.example.eventms.hub.service.IOesRefundItemService;
import com.example.eventms.mbp.entity.OesOrderAttendee;
import com.example.eventms.mbp.entity.OesRefundItem;
import com.example.eventms.mbp.entity.OesRefundRequest;
import com.example.eventms.mbp.mapper.EesTicketStockMapper;
import com.example.eventms.mbp.mapper.OesOrderAttendeeMapper;
import com.example.eventms.mbp.mapper.OesRefundItemMapper;
import com.example.eventms.mbp.mapper.OesRefundRequestMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.eventms.hub.dto.RefundApplyPayload.RequestItem;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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
public class OesRefundItemServiceImpl extends ServiceImpl<OesRefundItemMapper, OesRefundItem> implements IOesRefundItemService {
    EesTicketStockMapper ticketStockMapper;
    OesRefundRequestMapper refundRequestMapper;
    OesOrderAttendeeMapper orderAttendeeMapper;

    @Override
    @Transactional
    public boolean approve(Long eventId, Long refundRequestId, RefundApplyPayload payload) {
        List<RequestItem> requestItems = payload.getRequestItems();

        OesRefundRequest refundReq = getRefundReq(refundRequestId);

        // event ownership should be checked
        // but I skip it here
        if (Objects.equals(eventId, refundReq.getEventId())) {
            Asserts.fail("Request not found");
        }

        List<Long> refundReqAttendeeIds = requestItems.stream()
                .map(RequestItem::getAttendeeId)
                .toList();
        List<OesOrderAttendee> attendeesToRefund = getRealAttendees(refundReq.getOrderId(), refundReqAttendeeIds);

        List<OesRefundItem> refundItems = new ArrayList<>();
        for (OesOrderAttendee a : attendeesToRefund) {
            OesRefundItem item = new OesRefundItem();
            item.setRefundRequestId(refundRequestId);
            item.setTicketId(a.getTicketId());
            item.setTicketName(a.getTicketName());
            item.setOrderTicketPrice(a.getTicketPrice());
            item.setOrderTicketQuantity(a.getTicketQuantity());
            item.setRealAmount(a.getRealAmount());
            item.setCouponAmount(a.getCouponAmount());
            item.setRefundQty(1);
            item.setRefundAmount(a.getRealAmount().multiply(BigDecimal.valueOf(1)));
            item.setBillReceiverEmail(a.getBillReceiverEmail());
            item.setReceiverName(a.getReceiverName());
            refundItems.add(item);
        }

        // no payment processing

        // restore ticket qty
        Map<Long, Long> ticketIdAndQtyToRefund = attendeesToRefund.stream()
                .collect(groupingBy(OesOrderAttendee::getTicketId, counting()));
        for (Long ticketId : ticketIdAndQtyToRefund.keySet()) {
            int count = ticketStockMapper.restoreTicketQty(ticketId, ticketIdAndQtyToRefund.get(ticketId));
            if (count == 0) Asserts.fail("Insufficient stock, unable to process request");
        }

        return saveBatch(refundItems);
    }

    private OesRefundRequest getRefundReq(Long refundRequestId) {
        var refundReqWrp = new LambdaQueryWrapper<OesRefundRequest>();
        refundReqWrp.eq(OesRefundRequest::getId, refundRequestId);
        OesRefundRequest refundRequest = refundRequestMapper.selectOne(refundReqWrp);
        if (refundRequest == null) Asserts.fail("Request not found");
        return refundRequest;
    }

    private List<OesOrderAttendee> getRealAttendees(Long orderId, List<Long> ids) {
        var orderAttendeeWrp = new LambdaQueryWrapper<OesOrderAttendee>();
        orderAttendeeWrp.eq(OesOrderAttendee::getOrderId, orderId).in(OesOrderAttendee::getId, ids);
        return orderAttendeeMapper.selectList(orderAttendeeWrp);
    }
}
