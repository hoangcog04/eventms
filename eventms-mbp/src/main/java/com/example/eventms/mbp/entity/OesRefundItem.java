package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

    private Long orderId;

    private String orderSn;

    private Long ticketId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    private String ticketName;

    private BigDecimal orderTicketPrice;

    @Schema(description = "Order ticket quantity")
    private Integer orderTicketQuantity;

    @Schema(description = "Actual payment amount")
    private BigDecimal realAmount;

    @Schema(description = "Coupon discount amount")
    private BigDecimal couponAmount;

    @Schema(description = "Quantity refunded")
    private Integer quantityProcessed;

    @Schema(description = "The amount of money refunded")
    private BigDecimal amountProcessed;
}
