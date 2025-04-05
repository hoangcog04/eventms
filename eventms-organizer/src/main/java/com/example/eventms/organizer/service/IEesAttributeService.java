package com.example.eventms.organizer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.mbp.entity.EesAttribute;
import com.example.eventms.organizer.dto.EventAttrInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IEesAttributeService extends IService<EesAttribute> {

    List<EventAttrInfo> getAttrInfo();
}
