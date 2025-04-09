package com.example.eventms.organizer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class TicketPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String summary;

    private String pic;

    private BigDecimal price;

    @Schema(description = "Initial number of this ticket")
    private Integer capacity;

    @Schema(description = "Maximum number per order (blank uses default value 10)")
    private Integer maxQuantityPerOrder;

    private Integer sorting;

    private Integer isFree;

    private Integer isHidden;
}
