package com.example.eventms.hub.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.hub.dto.OrderCompletion;
import com.example.eventms.hub.dto.OrderParam;
import com.example.eventms.hub.dto.OrderResult;
import com.example.eventms.mbp.entity.OesOrder;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IOesOrderService extends IService<OesOrder> {

    @Transactional
    OrderResult generateOrder(OrderParam orderParam);

    @Transactional
    Integer paySuccess(Long orderId, OrderCompletion payload);
}
