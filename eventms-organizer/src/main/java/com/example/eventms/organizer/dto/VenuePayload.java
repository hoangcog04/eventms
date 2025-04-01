package com.example.eventms.organizer.dto;

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
public class VenuePayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String city;

    private String country;

    private String detailAddress;

    private String googlePlaceId;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
