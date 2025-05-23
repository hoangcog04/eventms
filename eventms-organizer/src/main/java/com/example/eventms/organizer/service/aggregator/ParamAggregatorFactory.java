package com.example.eventms.organizer.service.aggregator;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.eventms.common.constant.ParamConstants;
import com.example.eventms.mbp.entity.*;
import com.example.eventms.mbp.mapper.*;
import com.example.eventms.organizer.dto.EventDetail;
import com.example.eventms.organizer.mapper.EventConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ParamAggregatorFactory {
    EesFaqMapper faqMapper;
    EesVenueMapper venueMapper;
    EesAgendaMapper agendaMapper;
    UesOrganizerMapper organizerMapper;
    EesCheckoutSettingMapper checkoutSettingMapper;
    EventConverter eventConverter;

    @Bean(name = ParamConstants.VENUE_EXPANSION)
    public ParamAggregator venueParamAggregator() {
        return (data, event) -> {
            EesVenue eesVenue = venueMapper.selectById(event.getVenueId());
            EventDetail.VenueDto venueDto = eventConverter.toVenueDetailDto(eesVenue);
            data.setVenue(venueDto);
        };
    }

    @Bean(name = ParamConstants.ORGANIZER_EXPANSION)
    public ParamAggregator organizerParamAggregator() {
        return (data, event) -> {
            UesOrganizer uesOrganizer = organizerMapper.selectById(event.getOrganizerId());
            EventDetail.OrganizerDto organizerDto = eventConverter.toOrganizerDetailDto(uesOrganizer);
            data.setOrganizer(organizerDto);
        };
    }

    @Bean(ParamConstants.CHECKOUT_SETTING_EXPANSION)
    public ParamAggregator checkoutSettingParamAggregator() {
        return (data, event) -> {
            var checkoutSettingWrp = new LambdaQueryWrapper<EesCheckoutSetting>();
            checkoutSettingWrp.eq(EesCheckoutSetting::getEventId, event.getId());
            List<EesCheckoutSetting> eesCheckoutSettings = checkoutSettingMapper.selectList(checkoutSettingWrp);
            List<EventDetail.CheckoutSettingDto> checkoutSettingDtos = eventConverter.toCheckoutSettingDtos(eesCheckoutSettings);
            data.setCheckoutSettings(checkoutSettingDtos);
        };
    }

    @Bean(ParamConstants.PROPERTY_EXPANSION)
    public ParamAggregator propertyParamAggregator() {
        return (data, event) -> {
            var agendaWrp = new LambdaQueryWrapper<EesAgenda>();
            agendaWrp.eq(EesAgenda::getEventId, event.getId());
            List<EesAgenda> eesAgendas = agendaMapper.selectList(agendaWrp);
            List<EventDetail.AgendaDto> agendaDtos = eventConverter.toAgendaDtos(eesAgendas);
            data.setAgendas(agendaDtos);

            var faqWrp = new LambdaQueryWrapper<EesFaq>();
            faqWrp.eq(EesFaq::getEventId, event.getId());
            List<EesFaq> eesFaqs = faqMapper.selectList(faqWrp);
            List<EventDetail.FaqDto> faqDtos = eventConverter.toFaqDtos(eesFaqs);
            data.setFaqs(faqDtos);
        };
    }
}
