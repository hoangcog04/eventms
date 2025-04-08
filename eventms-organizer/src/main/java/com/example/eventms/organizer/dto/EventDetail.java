package com.example.eventms.organizer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class EventDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private EventDto event;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VenueDto venue;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganizerDto organizer;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CheckoutSettingDto> checkoutSettings;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AgendaDto> agendas;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FaqDto> faqs;

    private List<AttributeDto> attributes;

    private List<AttributeValueDto> attributeValues;

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

        private Integer isFree;

        private LocalDateTime created;

        private LocalDateTime changed;

        private LocalDateTime publishTime;
    }

    @Getter
    @Setter
    public static class VenueDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String name;

        private String city;

        private String country;

        private String detailAddress;

        private String googlePlaceId;

        private BigDecimal latitude;

        private BigDecimal longitude;
    }

    @Getter
    @Setter
    public static class CheckoutSettingDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String countryCode;

        private String currencyCode;

        @Schema(description = "0->Default; 1->Offline; 2->Payments system")
        private Integer checkoutMethod;

        @Schema(description = "Left empty if checkout method is not offline")
        private String offlineNote;

        private LocalDateTime created;

        private LocalDateTime changed;
    }

    @Getter
    @Setter
    public static class AgendaDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String sessionName;

        private String summary;

        private String hostName;

        private LocalTime startTime;

        private LocalTime endTime;
    }

    @Getter
    @Setter
    public static class FaqDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String question;

        private String answer;

        private Integer sorting;
    }

    @Getter
    @Setter
    public static class OrganizerDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String name;

        private String summary;

        private Integer eventCount;

        private String websiteUrl;

        private LocalDateTime created;

        private LocalDateTime changed;

        private String avatar;

        private String bigPic;
    }

    @Getter
    @Setter
    public static class AttributeDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String attributeCategoryName;

        private String name;

        @Schema(description = "Selection type: 0->no selection; 1->single; 2->multiple")
        private Integer selectType;

        @Schema(description = "Entry method: 0->no entry; 1->manual; 2->select from list")
        private Integer inputType;

        @Schema(description = "Value list, separated by commas")
        private String inputList;
    }

    @Getter
    @Setter
    public static class AttributeValueDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long attributeId;

        @Schema(description = "The value of the attr's value list, separated by commas")
        private String value;
    }
}
