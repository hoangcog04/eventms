package com.example.eventms.organizer.mapper;

import com.example.eventms.mbp.entity.EesTicket;
import com.example.eventms.organizer.dto.TicketItem;
import com.example.eventms.organizer.dto.TicketPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

/**
 * @author vicendy04
 * @since 2025-03
 */
@Mapper(componentModel = "spring")
public interface TicketConverter {
    EesTicket toEntity(TicketPayload dto);

    TicketItem toItem(EesTicket entity);

    @Mapping(source = "price", target = "price")
    @Mapping(source = "currency", target = "currency")
    TicketItem.Cost toCost(BigDecimal price, String currency);

    default TicketItem toDto(EesTicket entity, BigDecimal price, String currency) {
        TicketItem ticketItem = toItem(entity);
        ticketItem.setCost(toCost(price, currency));
        return ticketItem;
    }
}
