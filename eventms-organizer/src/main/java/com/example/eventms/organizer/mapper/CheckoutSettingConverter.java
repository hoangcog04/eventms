package com.example.eventms.organizer.mapper;

import com.example.eventms.mbp.entity.EesCheckoutSetting;
import com.example.eventms.organizer.dto.CheckoutSettingDetail;
import com.example.eventms.organizer.dto.CheckoutSettingPayload;
import com.example.eventms.organizer.dto.CheckoutSettingResult;
import org.mapstruct.Mapper;

/**
 * @author vicendy04
 * @since 2025-03
 */
@Mapper(componentModel = "spring")
public interface CheckoutSettingConverter {
    EesCheckoutSetting toEntity(CheckoutSettingPayload dto);

    CheckoutSettingDetail toDetailDto(EesCheckoutSetting entity);

    CheckoutSettingResult toResultDto(EesCheckoutSetting entity);
}
