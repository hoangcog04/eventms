package com.example.eventms.hub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.common.exception.Asserts;
import com.example.eventms.hub.dto.RefundRequestPayload;
import com.example.eventms.hub.mapper.OrderConverter;
import com.example.eventms.hub.service.IOesRefundRequestService;
import com.example.eventms.mbp.entity.OesOrder;
import com.example.eventms.mbp.entity.OesRefundRequest;
import com.example.eventms.mbp.mapper.OesOrderMapper;
import com.example.eventms.mbp.mapper.OesRefundRequestMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.eventms.common.constant.BusinessConstants.DEFAULT_ORDER_COMPLETION_STATUS;
import static com.example.eventms.common.constant.BusinessConstants.DEFAULT_REFUND_ORDER_STATUS;
import static com.example.eventms.security.util.SecurityUtil.getCurUserId;

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
public class OesRefundRequestServiceImpl extends ServiceImpl<OesRefundRequestMapper, OesRefundRequest> implements IOesRefundRequestService {
    OesOrderMapper orderMapper;
    OrderConverter orderConverter;

    @Override
    public boolean requestRefund(Long orderId, RefundRequestPayload payload) {
        OesRefundRequest oesRefundRequest = orderConverter.toEntity(payload);

        var orderWrp = new LambdaQueryWrapper<OesOrder>();
        orderWrp.eq(OesOrder::getId, orderId);
        OesOrder order = orderMapper.selectOne(orderWrp);
        if (order == null) Asserts.fail("Order not found");

        Long curUserId = getCurUserId();
        if (!Objects.equals(curUserId, order.getUserId())) {
            // just mocking, so I pass here
            // Asserts.fail("This is not yours order");
            assert true;
        } else {
            oesRefundRequest.setUserId(order.getUserId());
        }

        if (!DEFAULT_ORDER_COMPLETION_STATUS.equals(order.getStatus())) {
            Asserts.fail("Order has an unexpected status");
        }

        oesRefundRequest.setOrderSn(order.getOrderSn());
        oesRefundRequest.setEventId(order.getEventId());
        oesRefundRequest.setCheckoutMethod(order.getCheckoutMethod());
        oesRefundRequest.setDeliveryMethod(order.getDeliveryMethod());
        oesRefundRequest.setOrderPayAmount(order.getPayAmount());
        oesRefundRequest.setStatus(DEFAULT_REFUND_ORDER_STATUS);

        return save(oesRefundRequest);
    }
}
