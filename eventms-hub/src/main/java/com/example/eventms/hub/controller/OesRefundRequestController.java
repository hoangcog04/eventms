package com.example.eventms.hub.controller;

import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.hub.dto.RefundApplyPayload;
import com.example.eventms.hub.dto.RefundRequestPayload;
import com.example.eventms.hub.service.IOesRefundRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Tag(name = "OesRefundRequestController", description = "Order Refund Management")
@RequestMapping("/orders")
public class OesRefundRequestController {
    IOesRefundRequestService refundRequestService;

    @RequestMapping(value = "/{orderId}/request_refund", method = RequestMethod.POST)
    public CommonResult<Boolean> requestRefund(@PathVariable Long orderId,
                                               @RequestBody RefundRequestPayload payload) {
        boolean requested = refundRequestService.requestRefund(orderId, payload);
        if (requested) return CommonResult.success(true);
        return CommonResult.failed();
    }
}
