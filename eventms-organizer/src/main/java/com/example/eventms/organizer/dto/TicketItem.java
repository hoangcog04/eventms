package com.example.eventms.organizer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class TicketItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long eventId;

    private String name;

    private String summary;

    private String pic;

    private Cost cost;

    @Schema(description = "Initial number of this ticket")
    private Integer capacity;

    @Schema(description = "The number of sold tickets")
    private Integer quantitySold;

    @Schema(description = "Maximum number per order (blank uses default value 10)")
    private Integer maxQuantityPerOrder;

    private Integer sorting;

    private Integer isFree;

    private Integer isHidden;

    private LocalDateTime created;

    private LocalDateTime changed;

    @Getter
    @Setter
    public static class Cost implements Serializable {

        private static final long serialVersionUID = 1L;

        private BigDecimal price;

        private String currency;
    }
}
