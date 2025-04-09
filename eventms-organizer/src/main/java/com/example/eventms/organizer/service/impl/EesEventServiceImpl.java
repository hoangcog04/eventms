package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.mbp.entity.*;
import com.example.eventms.mbp.mapper.*;
import com.example.eventms.organizer.constant.PayloadConstants;
import com.example.eventms.organizer.dto.*;
import com.example.eventms.organizer.dto.EventContent.Module;
import com.example.eventms.organizer.dto.EventContent.Widget;
import com.example.eventms.organizer.mapper.EventConverter;
import com.example.eventms.organizer.service.IEesEventService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.eventms.common.constant.ParamConstants.*;
import static com.example.eventms.organizer.constant.BusinessConstants.*;
import static com.example.eventms.organizer.constant.ValidateErrConstants.*;
import static com.example.eventms.organizer.dto.EventContent.WidgetType;
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
    EesVenueMapper venueMapper;
    EesAgendaMapper agendaMapper;
    EesTicketMapper ticketMapper;
    EesAttributeMapper attributeMapper;
    UesOrganizerMapper organizerMapper;
    EesTicketStockMapper ticketStockMapper;
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
        save(eesEvent);

        eesTicket.setEventId(eesEvent.getId());
        eesTicket.setName(FIRST_TICKET_NAME);
        eesTicket.setCapacity(eesEvent.getCapacity());
        eesTicket.setSorting(FIRST_TICKET_SORTING);
        ticketMapper.insert(eesTicket);

        EesTicketStock ticketStock = new EesTicketStock();
        ticketStock.setTicketId(eesTicket.getId());
        ticketStock.setQuantityAvailable(eesTicket.getCapacity());
        ticketStock.setQuantityLock(DEFAULT_QUANTITY_LOCK);
        ticketStockMapper.insert(ticketStock);

        EesCheckoutSetting checkoutStg = new EesCheckoutSetting();
        checkoutStg.setEventId(eesEvent.getId());
        checkoutStg.setCheckoutMethod(DEFAULT_CHECKOUT_METHOD);
        checkoutSettingMapper.insert(checkoutStg);

        eesEvent = getById(eesEvent.getId());
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

        EesEvent eesEvent = getById(eventId);
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

        if (paramList.contains(PROPERTY_EXPANSION)) {
            var agendaWrp = new LambdaQueryWrapper<EesAgenda>();
            agendaWrp.eq(EesAgenda::getEventId, eventId);
            List<EesAgenda> eesAgendas = agendaMapper.selectList(agendaWrp);
            List<EventDetail.AgendaDto> agendaDtos = eventConverter.toAgendaDtos(eesAgendas);
            eventDetail.setAgendas(agendaDtos);

            var faqWrp = new LambdaQueryWrapper<EesFaq>();
            faqWrp.eq(EesFaq::getEventId, eventId);
            List<EesFaq> eesFaqs = faqMapper.selectList(faqWrp);
            List<EventDetail.FaqDto> faqDtos = eventConverter.toFaqDtos(eesFaqs);
            eventDetail.setFaqs(faqDtos);
        }

        return eventDetail;
    }

    @Override
    public EventPublish publish(Long eventId) {
        EventPublish eventPublish = new EventPublish();

        EesEvent eesEvent = getById(eventId);
        if (isEventInvalid(eesEvent)) eventPublish.setErrorMessage(EVENT_FAIL_MSG);

        UesOrganizer uesOrganizer = organizerMapper.selectById(eesEvent.getOrganizerId());
        if (isOrganizerInValid(uesOrganizer)) eventPublish.setErrorMessage(ORGANIZER_FAIL_MSG);

        var ticketWrp = new LambdaQueryWrapper<EesTicket>();
        ticketWrp.eq(EesTicket::getEventId, eventId);
        List<EesTicket> eesTickets = ticketMapper.selectList(ticketWrp);
        if (isTicketsInvalid(eesTickets)) eventPublish.setErrorMessage(TICKET_FAIL_MSG);

        eesEvent.setPublishTime(LocalDateTime.now());
        updateById(eesEvent);

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

        EesEvent eesEvent = eventConverter.toEventFrom(modules);
        eesEvent.setId(eventId);

        List<EesAgenda> eesAgendas = new ArrayList<>();
        List<EesFaq> eesFaqs = new ArrayList<>();

        for (Widget w : widgets) {
            WidgetType wType = w.getType();
            JsonNode wData = w.getData();

            if (wType == WidgetType.AGENDA) eesAgendas = mergeWithExistingAgenda(eventId, wData);
            else if (wType == WidgetType.FAQS) eesFaqs = mergeWithExistingFaq(eventId, wData);
        }

        updateById(eesEvent);
        agendaMapper.insertOrUpdate(eesAgendas);
        faqMapper.insertOrUpdate(eesFaqs);
    }

    private List<EesAgenda> mergeWithExistingAgenda(Long eventId, JsonNode wData) {
        var agendaWrp = new LambdaQueryWrapper<EesAgenda>();
        agendaWrp.select(EesAgenda::getId).eq(EesAgenda::getEventId, eventId);
        List<Long> agendaIdsDb = agendaMapper.selectObjs(agendaWrp);

        List<EesAgenda> eesAgendas = new ArrayList<>();

        JsonNode slots = wData.path(PayloadConstants.slots); // list
        for (JsonNode slot : slots) {
            EesAgenda eesAgenda = eventConverter.toAgenda(eventId, slot);
            Long id = slot.path(EesAgenda.Fields.id).asLong();

            if (agendaIdsDb.contains(id)) // update
                eesAgenda.setId(id);

            eesAgendas.add(eesAgenda);
        }

        return eesAgendas;
    }

    private List<EesFaq> mergeWithExistingFaq(Long eventId, JsonNode wData) {
        var faqWrp = new LambdaQueryWrapper<EesFaq>();
        faqWrp.select(EesFaq::getId).eq(EesFaq::getEventId, eventId);
        List<Long> faqIdsDb = faqMapper.selectObjs(faqWrp);

        List<EesFaq> eesFaqs = new ArrayList<>();

        JsonNode faqs = wData.path(PayloadConstants.faqs); // list
        for (JsonNode faq : faqs) {
            EesFaq eesFaq = eventConverter.toFaq(eventId, faq);
            Long id = faq.path(EesFaq.Fields.id).asLong();

            if (faqIdsDb.contains(id)) // update
                eesFaq.setId(id);

            eesFaqs.add(eesFaq);
        }

        return eesFaqs;
    }
}
