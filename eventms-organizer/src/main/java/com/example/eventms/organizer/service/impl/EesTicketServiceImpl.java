package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.mbp.entity.EesCheckoutSetting;
import com.example.eventms.mbp.entity.EesTicket;
import com.example.eventms.mbp.mapper.EesCheckoutSettingMapper;
import com.example.eventms.mbp.mapper.EesTicketMapper;
import com.example.eventms.organizer.dto.TicketItem;
import com.example.eventms.organizer.dto.TicketPayload;
import com.example.eventms.organizer.mapper.TicketConverter;
import com.example.eventms.organizer.service.IEesTicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.example.eventms.organizer.constant.BusinessConstants.DEFAULT_CHECKOUT_METHOD;

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
public class EesTicketServiceImpl extends ServiceImpl<EesTicketMapper, EesTicket> implements IEesTicketService {
    EesTicketMapper ticketMapper;
    EesCheckoutSettingMapper checkoutStgMapper;
    TicketConverter ticketConverter;

    @Override
    public TicketItem add(Long eventId, TicketPayload ticketPayload) {
        EesTicket eesTicket = ticketConverter.toEntity(ticketPayload);
        eesTicket.setEventId(eventId);
        ticketMapper.insert(eesTicket);

        eesTicket = ticketMapper.selectById(eesTicket.getId());

        String currency = getCurrency(eventId);
        return ticketConverter.toDto(eesTicket, eesTicket.getPrice(), currency);
    }

    @Override
    public IPage<TicketItem> getList(Long eventId, Integer pageNum, Integer pageSize) {
        var wrp = new LambdaQueryWrapper<EesTicket>();
        wrp.eq(EesTicket::getEventId, eventId);
        Page<EesTicket> page = new Page<>(pageNum, pageSize);

        String currency = getCurrency(eventId);
        return page(page, wrp).convert(ticket -> ticketConverter.toDto(ticket, ticket.getPrice(), currency));
    }

    private String getCurrency(Long eventId) {
        var checkoutStgWrp = new LambdaQueryWrapper<EesCheckoutSetting>();
        checkoutStgWrp.eq(EesCheckoutSetting::getEventId, eventId).eq(EesCheckoutSetting::getCheckoutMethod, DEFAULT_CHECKOUT_METHOD);
        EesCheckoutSetting ctg = checkoutStgMapper.selectOne(checkoutStgWrp);
        return ctg.getCurrencyCode();
    }
}
