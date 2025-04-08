package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.mbp.entity.*;
import com.example.eventms.mbp.mapper.*;
import com.example.eventms.organizer.dto.*;
import com.example.eventms.organizer.dto.EventContent.Module;
import com.example.eventms.organizer.dto.EventContent.Widget;
import com.example.eventms.organizer.mapper.EventConverter;
import com.example.eventms.organizer.service.IEesEventService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventms.common.constant.ParamConstants.*;
import static com.example.eventms.organizer.constant.BusinessConstants.FIRST_TICKET_NAME;
import static com.example.eventms.organizer.constant.BusinessConstants.FIRST_TICKET_SORTING;
import static com.example.eventms.organizer.constant.ValidateErrConstants.*;
import static com.example.eventms.organizer.utils.ValidationUtils.*;

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
    EesFaqMapper faqMapper;
    EesEventMapper eventMapper;
    EesVenueMapper venueMapper;
    EesAgendaMapper agendaMapper;
    EesTicketMapper ticketMapper;
    EesAttributeMapper attributeMapper;
    UesOrganizerMapper organizerMapper;
    EesAttributeValueMapper attributeValueMapper;
    EesCheckoutSettingMapper checkoutSettingMapper;
    EventConverter eventConverter;

    @Override
    public EventResult autoCreate(EventPayload eventPayload) {
        EesEvent eesEvent = eventConverter.toEntity(eventPayload.getEvent());
        EesTicket eesTicket = eventConverter.toEntity(eventPayload.getTicket());

        // Todo: edit organizerId
        eesEvent.setOrganizerId(4L);
        if (eesTicket.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            eesEvent.setIsFree(1);
            eesTicket.setIsFree(1);
        }
        eventMapper.insert(eesEvent);

        eesTicket.setEventId(eesEvent.getId());
        eesTicket.setName(FIRST_TICKET_NAME);
        eesTicket.setQuantityTotal(eesEvent.getCapacity());
        eesTicket.setCapacity(eesEvent.getCapacity());
        eesTicket.setSorting(FIRST_TICKET_SORTING);
        ticketMapper.insert(eesTicket);

        eesEvent = eventMapper.selectById(eesEvent.getId());
        eesTicket = ticketMapper.selectById(eesTicket.getId());

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

        EesEvent eesEvent = eventMapper.selectById(eventId);
        EventDetail.EventDto eventDto = eventConverter.toEventDetailDto(eesEvent);
        eventDetail.setEvent(eventDto);

        var attrValueWrp = new LambdaQueryWrapper<EesAttributeValue>();
        attrValueWrp.eq(EesAttributeValue::getEventId, eventId);
        List<EesAttributeValue> attrValues = attributeValueMapper.selectList(attrValueWrp);
        List<EventDetail.AttributeValueDto> attrValueDetailDtos = eventConverter.toAttrValueDetailDtos(attrValues);
        eventDetail.setAttributeValues(attrValueDetailDtos);

        if (!CollectionUtils.isEmpty(attrValues)) {
            List<Long> attributeIds = attrValues.stream().map(EesAttributeValue::getAttributeId).toList();

            var attrWrp = new LambdaQueryWrapper<EesAttribute>();
            attrWrp.in(EesAttribute::getId, attributeIds);
            List<EesAttribute> eesAttributes = attributeMapper.selectList(attrWrp);
            List<EventDetail.AttributeDto> attrDetailDtos = eventConverter.toAttrDetailDtos(eesAttributes);
            eventDetail.setAttributes(attrDetailDtos);
        }

        if (paramList.contains(VENUE_EXPANSION)) {
            EesVenue eesVenue = venueMapper.selectById(eesEvent.getVenueId());
            EventDetail.VenueDto venueDto = eventConverter.toVenueDetailDto(eesVenue);
            eventDetail.setVenue(venueDto);
        }

        if (paramList.contains(ORGANIZER_EXPANSION)) {
            UesOrganizer uesOrganizer = organizerMapper.selectById(eesEvent.getOrganizerId());
            EventDetail.OrganizerDto organizerDto = eventConverter.toOrganizerDetailDto(uesOrganizer);
            eventDetail.setOrganizer(organizerDto);
        }

        if (paramList.contains(CHECKOUT_SETTING_EXPANSION)) {
            var checkoutSettingWrp = new LambdaQueryWrapper<EesCheckoutSetting>();
            checkoutSettingWrp.eq(EesCheckoutSetting::getEventId, eventId);
            List<EesCheckoutSetting> eesCheckoutSettings = checkoutSettingMapper.selectList(checkoutSettingWrp);
            List<EventDetail.CheckoutSettingDto> checkoutSettingDtos = eventConverter.toCheckoutSettingDtos(eesCheckoutSettings);
            eventDetail.setCheckoutSettings(checkoutSettingDtos);
        }

        return eventDetail;
    }

    @Override
    public EventPublish publish(Long eventId) {
        EventPublish eventPublish = new EventPublish();

        EesEvent eesEvent = eventMapper.selectById(eventId);
        if (isEventInvalid(eesEvent)) eventPublish.setErrorMessage(EVENT_FAIL_MSG);

        UesOrganizer uesOrganizer = organizerMapper.selectById(eesEvent.getOrganizerId());
        if (isOrganizerInValid(uesOrganizer)) eventPublish.setErrorMessage(ORGANIZER_FAIL_MSG);

        var ticketWrp = new LambdaQueryWrapper<EesTicket>();
        ticketWrp.eq(EesTicket::getEventId, eventId);
        List<EesTicket> eesTickets = ticketMapper.selectList(ticketWrp);
        if (isTicketsInvalid(eesTickets)) eventPublish.setErrorMessage(TICKET_FAIL_MSG);

        eesEvent.setPublishTime(LocalDateTime.now());
        eventMapper.updateById(eesEvent);

        return eventPublish;
    }

    @Override
    public EventPublish unpublish(Long eventId) {
        return null;
    }

    @Override
    public void content(Long eventId, EventContent eventContent) {
        List<Module> modules = eventContent.getModules();
        List<Widget> widgets = eventContent.getWidgets();

        EesEvent eesEvent = eventConverter.toEventFrom(eventId, modules);
        var pair = eventConverter.toWidgetsFrom(eventId, widgets);
        List<EesAgenda> eesAgendas = pair.getKey();
        List<EesFaq> eesFaqs = pair.getValue();

        this.saveOrUpdate(eesEvent);
        agendaMapper.insertOrUpdate(eesAgendas);
        faqMapper.insertOrUpdate(eesFaqs);
    }
}
