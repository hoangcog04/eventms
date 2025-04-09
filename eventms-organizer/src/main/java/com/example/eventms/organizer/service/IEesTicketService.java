package com.example.eventms.organizer.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.mbp.entity.EesTicket;
import com.example.eventms.organizer.dto.TicketItem;
import com.example.eventms.organizer.dto.TicketPayload;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IEesTicketService extends IService<EesTicket> {

    @Transactional
    TicketItem add(Long eventId, TicketPayload ticketPayload);

    IPage<TicketItem> getList(Long eventId, Integer pageNum, Integer pageSize);
}
