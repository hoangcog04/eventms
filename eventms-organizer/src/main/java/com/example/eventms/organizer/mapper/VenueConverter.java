package com.example.eventms.organizer.mapper;

import com.example.eventms.common.config.BaseConverter;
import com.example.eventms.mbp.entity.EesVenue;
import com.example.eventms.organizer.dto.VenuePayload;
import org.mapstruct.Mapper;

/**
 * @author vicendy04
 * @since 2025-03
 */
@Mapper(componentModel = "spring")
public interface VenueConverter extends BaseConverter<EesVenue, VenuePayload> {
}
