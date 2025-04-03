package com.example.eventms.organizer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.mbp.entity.EesCheckoutSetting;
import com.example.eventms.organizer.dto.CheckoutSettingDetail;
import com.example.eventms.organizer.dto.CheckoutSettingPayload;
import com.example.eventms.organizer.dto.CheckoutSettingResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IEesCheckoutSettingService extends IService<EesCheckoutSetting> {

    CheckoutSettingDetail detail(Long checkoutSettingId);

    CheckoutSettingResult checkoutSetting(Long eventId, CheckoutSettingPayload checkoutSettingPayload);
}
