package com.example.eventms.hub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.hub.dto.RefundApplyPayload;
import com.example.eventms.mbp.entity.OesRefundItem;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IOesRefundItemService extends IService<OesRefundItem> {

    @Transactional
    boolean approve(Long eventId, Long refundRequestId, RefundApplyPayload payload);
}
