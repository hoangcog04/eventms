package com.example.eventms.hub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.hub.dto.RefundRequestPayload;
import com.example.eventms.mbp.entity.OesRefundRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IOesRefundRequestService extends IService<OesRefundRequest> {

    @Transactional
    boolean requestRefund(Long orderId, RefundRequestPayload payload);
}
