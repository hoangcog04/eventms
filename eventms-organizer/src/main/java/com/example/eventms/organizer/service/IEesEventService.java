package com.example.eventms.organizer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.mbp.entity.EesEvent;
import com.example.eventms.organizer.dto.EventDetail;
import com.example.eventms.organizer.dto.EventPayload;
import com.example.eventms.organizer.dto.EventResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IEesEventService extends IService<EesEvent> {

    @Transactional
    EventResult autoCreate(EventPayload eventPayload);

    EventDetail detail(Long eventId, List<String> paramList);
}
