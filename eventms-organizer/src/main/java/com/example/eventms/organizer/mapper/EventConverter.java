package com.example.eventms.organizer.mapper;

import com.example.eventms.mbp.entity.*;
import com.example.eventms.organizer.dto.EventDetail;
import com.example.eventms.organizer.dto.EventPayload;
import com.example.eventms.organizer.dto.EventResult;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author vicendy04
 * @since 2025-03
 */
@Mapper(componentModel = "spring")
public interface EventConverter {
    EesEvent toEntity(EventPayload.EventDto dto);

    EesTicket toEntity(EventPayload.TicketDto dto);

    EventResult.EventDto toEventResultDto(EesEvent entity);

    EventResult.TicketDto toTicketResultDto(EesTicket entity);

    EventDetail.EventDto toEventDetailDto(EesEvent entity);

    EventDetail.VenueDto toVenueDetailDto(EesVenue entity);

    EventDetail.OrganizerDto toOrganizerDetailDto(UesOrganizer entity);

    EventDetail.AttributeDto toAttrDetailDto(EesAttribute entity);

    EventDetail.AttributeValueDto toAttrValueDetailDto(EesAttributeValue entity);

    List<EventDetail.AttributeDto> toAttrDetailDtos(List<EesAttribute> entities);

    List<EventDetail.AttributeValueDto> toAttrValueDetailDtos(List<EesAttributeValue> entities);

}
