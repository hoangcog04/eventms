package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author vicendy04
 * @since 2025-04
 */
@Getter
@Setter
@ToString
@TableName("oes_refund_item")
@Schema(name = "OesRefundItem", description = "")
public class OesRefundItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long refundRequestId;

    private Long ticketId;

    private String ticketName;

    private BigDecimal orderTicketPrice;

    @Schema(description = "Order ticket quantity")
    private Integer orderTicketQuantity;

    @Schema(description = "Actual payment amount")
    private BigDecimal realAmount;

    @Schema(description = "Coupon discount amount")
    private BigDecimal couponAmount;

    @Schema(description = "Quantity refunded")
    private Integer refundQty;

    @Schema(description = "The amount of money refunded")
    private BigDecimal refundAmount;

    @Schema(description = "Quantity processed")
    private Integer qtyProcessed;

    @Schema(description = "The amount of money processed")
    private BigDecimal amountProcessed;

    @Schema(description = "Same information as Order owner")
    private String billReceiverEmail;

    @Schema(description = "Same information as Order owner")
    private String receiverName;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changed;
}
