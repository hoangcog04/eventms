package com.example.eventms.hub.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
@Getter
@Setter
public class OrderParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long eventId;

    // Todo:
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long couponId;

    private List<TicketDto> attrCategory;

    @Getter
    @Setter
    public static class TicketDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long ticketId;
    }
}
