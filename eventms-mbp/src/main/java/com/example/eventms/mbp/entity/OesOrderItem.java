package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@ToString
@TableName("oes_order_item")
@Schema(name = "OesOrderItem", description = "")
public class OesOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    @Schema(description = "Order serial number")
    private String orderSn;

    private Long ticketId;

    private String ticketName;

    private String ticketPic;

    private Integer ticketQuantity;

    private BigDecimal ticketPrice;

    @Schema(description = "Actual payment amount")
    private BigDecimal realAmount;

    @Schema(description = "Coupon discount amount")
    private BigDecimal couponAmount;
}
