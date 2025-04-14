package com.example.eventms.hub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class OrderResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AttendeeDto> attendees;

    private OrderDto order;

    @Getter
    @Setter
    public static class AttendeeDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long orderId;

        private Long ticketId;

        private String ticketName;

        private String ticketPic;

        private AttendeeCost cost;

        @Schema(description = "Same information as Order owner")
        private String billReceiverEmail;

        @Schema(description = "Same information as Order owner")
        private String receiverName;

        private Integer refunded;

        private LocalDateTime created;

        private LocalDateTime changed;
    }

    @Getter
    @Setter
    public static class AttendeeCost implements Serializable {

        private static final long serialVersionUID = 1L;

        private BigDecimal ticketPrice;

        private Integer ticketQuantity;

        @Schema(description = "Actual payment amount")
        private BigDecimal realAmount;

        @Schema(description = "Coupon discount amount")
        private BigDecimal couponAmount;
    }

    @Getter
    @Setter
    public static class OrderDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long userId;

        private Long eventId;

        @Schema(description = "Order owner name")
        private String ownerName;

        @Schema(description = "0->Default;1->Started;2->Pending;3->Completed;4->Abandoned")
        private Integer status;

        @Schema(description = "0->Email;1->Phone")
        private Integer deliveryMethod;

        @Schema(description = "Email for invoice (optional)")
        private String billReceiverEmail;

        @Schema(description = "Attendee name (required)")
        private String receiverName;

        private OrderCost cost;

        private LocalDateTime created;

        private LocalDateTime changed;
    }

    @Getter
    @Setter
    public static class OrderCost implements Serializable {

        private static final long serialVersionUID = 1L;

        @Schema(description = "Total order amount")
        private BigDecimal totalAmount;

        @Schema(description = "Coupon discount amount")
        private BigDecimal couponAmount;

        @Schema(description = "0->Default; 1->Offline; 2->Payments system")
        private Integer checkoutMethod;
    }
}
