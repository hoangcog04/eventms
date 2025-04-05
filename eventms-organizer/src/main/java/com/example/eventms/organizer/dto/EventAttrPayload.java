package com.example.eventms.organizer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
public class EventPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private EventDto event;

    private TicketDto ticket;

    @Getter
    @Setter
    public static class EventDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long venueId;

        private String title;

        private String summary;

        private LocalDateTime date;

        private LocalTime startTime;

        private LocalTime endTime;

        @Schema(description = "Set event capacity to prevent overselling")
        private Integer capacity;
    }

    @Getter
    @Setter
    public static class TicketDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private BigDecimal price;

        @Schema(description = "Maximum number per order (blank uses default value 10)")
        private Integer maxQuantityPerOrder;
    }
}
