package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.common.constant.BusinessConstants;
import com.example.eventms.mbp.entity.*;
import com.example.eventms.mbp.mapper.*;
import com.example.eventms.organizer.dto.EventDetail;
import com.example.eventms.organizer.dto.EventPayload;
import com.example.eventms.organizer.dto.EventResult;
import com.example.eventms.organizer.mapper.EventConverter;
import com.example.eventms.organizer.service.IEesEventService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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
public class EesEventServiceImpl extends ServiceImpl<EesEventMapper, EesEvent> implements IEesEventService {
    EesEventMapper eesEventMapper;
    EesVenueMapper eesVenueMapper;
    EesTicketMapper eesTicketMapper;
    EesAttributeMapper eesAttributeMapper;
    UesOrganizerMapper uesOrganizerMapper;
    EesAttributeValueMapper eesAttributeValueMapper;
    EventConverter eventConverter;

    @Override
    public EventResult autoCreate(EventPayload eventPayload) {
        EesEvent eesEvent = eventConverter.toEntity(eventPayload.getEvent());
        EesTicket eesTicket = eventConverter.toEntity(eventPayload.getTicket());

        eesEvent.setOrganizerId(4L);
        if (eesTicket.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            eesEvent.setIsFree(1);
            eesTicket.setIsFree(1);
        }
        eesEventMapper.insert(eesEvent);

        eesTicket.setEventId(eesEvent.getId());
        eesTicket.setName(BusinessConstants.FIRST_TICKET_NAME);
        eesTicket.setQuantityTotal(eesEvent.getCapacity());
        eesTicket.setCapacity(eesEvent.getCapacity());
        eesTicket.setSorting(BusinessConstants.FIRST_TICKET_SORTING);
        eesTicketMapper.insert(eesTicket);

        eesEvent = eesEventMapper.selectById(eesEvent.getId());
        eesTicket = eesTicketMapper.selectById(eesTicket.getId());

        EventResult.EventDto eventDto = eventConverter.toEventResultDto(eesEvent);
        EventResult.TicketDto ticketDto = eventConverter.toTicketResultDto(eesTicket);
        EventResult eventResult = new EventResult();
        eventResult.setEvent(eventDto);
        eventResult.setTicket(ticketDto);

        return eventResult;
    }

    @Override
    public EventDetail detail(Long eventId, List<String> paramList) {
        EventDetail eventDetail = new EventDetail();

        EesEvent eesEvent = eesEventMapper.selectById(eventId);
        EventDetail.EventDto eventDto = eventConverter.toEventDetailDto(eesEvent);
        eventDetail.setEvent(eventDto);

        var attrValueWrapper = new LambdaQueryWrapper<EesAttributeValue>();
        attrValueWrapper.eq(EesAttributeValue::getEventId, eventId);
        List<EesAttributeValue> attrValues = eesAttributeValueMapper.selectList(attrValueWrapper);
        List<EventDetail.AttributeValueDto> attrValueDetailDtos = eventConverter.toAttrValueDetailDtos(attrValues);
        eventDetail.setAttributeValueList(attrValueDetailDtos);

        if (!CollectionUtils.isEmpty(attrValues)) {
            List<Long> attributeIds = attrValues.stream().map(EesAttributeValue::getAttributeId).toList();

            var attrWrapper = new LambdaQueryWrapper<EesAttribute>();
            attrWrapper.in(EesAttribute::getId, attributeIds);
            List<EesAttribute> eesAttributes = eesAttributeMapper.selectList(attrWrapper);
            List<EventDetail.AttributeDto> attrDetailDtos = eventConverter.toAttrDetailDtos(eesAttributes);
            eventDetail.setAttributeList(attrDetailDtos);
        }

        if (paramList.contains(BusinessConstants.VENUE_EXPANSION_PARAM)) {
            EesVenue eesVenue = eesVenueMapper.selectById(eesEvent.getVenueId());
            EventDetail.VenueDto venueDto = eventConverter.toVenueDetailDto(eesVenue);
            eventDetail.setVenue(venueDto);
        }

        if (paramList.contains(BusinessConstants.ORGANIZER_EXPANSION_PARAM)) {
            UesOrganizer uesOrganizer = uesOrganizerMapper.selectById(eesEvent.getOrganizerId());
            EventDetail.OrganizerDto organizerDto = eventConverter.toOrganizerDetailDto(uesOrganizer);
            eventDetail.setOrganizer(organizerDto);
        }

        return eventDetail;
    }
}
