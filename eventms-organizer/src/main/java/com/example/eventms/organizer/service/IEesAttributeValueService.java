package com.example.eventms.organizer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.mbp.entity.EesAttributeValue;
import com.example.eventms.organizer.dto.EventProperty;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IEesAttributeValueService extends IService<EesAttributeValue> {

    List<EventProperty> getEventProperties(Long eventId);
}
