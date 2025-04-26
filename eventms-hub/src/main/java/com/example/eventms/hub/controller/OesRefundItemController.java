package com.example.eventms.hub.controller;

import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.hub.dto.RefundApplyPayload;
import com.example.eventms.hub.service.IOesRefundItemService;
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
@Tag(name = "OesRefundItemController", description = "Order Refund Management")
@RequestMapping("/events/{eventId}/refunds")
public class OesRefundItemController {
    IOesRefundItemService refundItemService;

    @RequestMapping(value = "/{refundRequestId}/approve", method = RequestMethod.POST)
    public CommonResult<Boolean> approve(@PathVariable Long eventId,
                                               @PathVariable Long refundRequestId,
                                               @RequestBody RefundApplyPayload payload) {
        boolean approved = refundItemService.approve(eventId, refundRequestId, payload);
        if (approved) return CommonResult.success(true);
        return CommonResult.failed();
    }
}
