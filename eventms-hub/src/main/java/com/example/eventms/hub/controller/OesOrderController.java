package com.example.eventms.hub.controller;

import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.hub.dto.OrderCompletion;
import com.example.eventms.hub.dto.OrderParam;
import com.example.eventms.hub.dto.OrderResult;
import com.example.eventms.hub.service.IOesOrderService;
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
@Tag(name = "OesOrderController", description = "Order Management")
@RequestMapping("/orders")
public class OesOrderController {
    IOesOrderService orderService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult<OrderResult> generateOrder(@RequestBody OrderParam orderParam) {
        OrderResult orderResult = orderService.generateOrder(orderParam);
        return CommonResult.success(orderResult);
    }

    @RequestMapping(value = "/{orderId}/complete", method = RequestMethod.POST)
    public CommonResult<Integer> paySuccess(@PathVariable Long orderId,
                                            @RequestBody OrderCompletion payload) {
        Integer count = orderService.paySuccess(orderId, payload);
        return CommonResult.success("Payment successful", count);
    }

    @RequestMapping(value = "/{orderId}/abandon", method = RequestMethod.POST)
    public CommonResult<Void> abandonOrder(@PathVariable Long orderId) {
        orderService.abandonOrder(orderId);
        return CommonResult.success("Successful");
    }
}
