package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.mbp.entity.EesCheckoutSetting;
import com.example.eventms.mbp.mapper.EesCheckoutSettingMapper;
import com.example.eventms.organizer.dto.CheckoutSettingDetail;
import com.example.eventms.organizer.dto.CheckoutSettingPayload;
import com.example.eventms.organizer.dto.CheckoutSettingResult;
import com.example.eventms.organizer.mapper.CheckoutSettingConverter;
import com.example.eventms.organizer.service.IEesCheckoutSettingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class EesCheckoutSettingServiceImpl extends ServiceImpl<EesCheckoutSettingMapper, EesCheckoutSetting> implements IEesCheckoutSettingService {
    CheckoutSettingConverter checkoutSettingConverter;


    @Override
    public CheckoutSettingDetail detail(Long checkoutSettingId) {
        EesCheckoutSetting eesCheckoutSetting = getById(checkoutSettingId);
        return checkoutSettingConverter.toDetailDto(eesCheckoutSetting);
    }

    @Transactional
    @Override
    public CheckoutSettingResult checkoutSetting(Long eventId, CheckoutSettingPayload payload) {
        // 0->Default; 1->Offline; 2->Payments system
        var checkoutSettingWrp = new LambdaQueryWrapper<EesCheckoutSetting>();
        checkoutSettingWrp.select(EesCheckoutSetting::getId)
                .eq(EesCheckoutSetting::getEventId, eventId)
                .eq(EesCheckoutSetting::getCheckoutMethod, payload.getCheckoutMethod());
        // select id
        List<Object> objects = this.listObjs(checkoutSettingWrp);

        EesCheckoutSetting checkoutSetting = checkoutSettingConverter.toEntity(payload);

        if (CollectionUtils.isEmpty(objects)) { // save
            checkoutSetting.setEventId(eventId);
            save(checkoutSetting); // has new id
        } else { // update
            checkoutSetting.setId((Long) objects.get(0));
            checkoutSetting.setEventId(eventId);
            updateById(checkoutSetting);
        }

        EesCheckoutSetting entity = getById(checkoutSetting.getId());
        return checkoutSettingConverter.toResultDto(entity);
    }
}
