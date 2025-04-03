package com.example.eventms.organizer.controller;

import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.organizer.dto.CheckoutSettingDetail;
import com.example.eventms.organizer.dto.CheckoutSettingPayload;
import com.example.eventms.organizer.dto.CheckoutSettingResult;
import com.example.eventms.organizer.service.IEesCheckoutSettingService;
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
@Tag(name = "EesCheckoutSettingController", description = "Event Checkout Management")
@RequestMapping("/organizations/events")
public class EesCheckoutSettingController {
    IEesCheckoutSettingService checkoutSettingService;

    @RequestMapping(value = "/checkout-settings/{checkout_setting_id}", method = RequestMethod.GET)
    public CommonResult<CheckoutSettingDetail> detail(@PathVariable Long checkout_setting_id) {
        CheckoutSettingDetail eventResult = checkoutSettingService.detail(checkout_setting_id);
        return CommonResult.success(eventResult);
    }

    @RequestMapping(value = "/{eventId}/checkout-settings", method = RequestMethod.POST)
    public CommonResult<CheckoutSettingResult> checkoutSetting(@PathVariable Long eventId,
                                                               @RequestBody CheckoutSettingPayload checkoutSettingPayload) {
        CheckoutSettingResult eventDetail = checkoutSettingService.checkoutSetting(eventId, checkoutSettingPayload);
        return CommonResult.success(eventDetail);
    }
}
