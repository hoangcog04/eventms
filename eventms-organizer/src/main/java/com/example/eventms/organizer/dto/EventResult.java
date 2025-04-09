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
public class EventResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private EventDto event;

    private TicketDto ticket;

    @Getter
    @Setter
    public static class EventDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long organizerId;

        private Long venueId;

        private String title;

        private String summary;

        private String pic;

        private String albumPics;

        private LocalDateTime date;

        private LocalTime startTime;

        private LocalTime endTime;

        @Schema(description = "0:Draft; 1:Live; 2:Started, 3: Ended, 4.Canceled")
        private Integer status;

        @Schema(description = "Set event capacity to prevent overselling")
        private Integer capacity;

        private Integer isFree;

        private LocalDateTime created;

        private LocalDateTime changed;

        private LocalDateTime publishTime;
    }

    @Getter
    @Setter
    public static class TicketDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long eventId;

        private String name;

        private String summary;

        private String pic;

        private BigDecimal price;

        @Schema(description = "Total available number of this ticket")
        private Integer quantityTotal;

        @Schema(description = "Maximum number per order (blank uses default value 10)")
        private Integer maxQuantityPerOrder;

        private Integer sorting;

        private Integer isFree;

        private Integer isHidden;

        private LocalDateTime created;

        private LocalDateTime changed;
    }
}
