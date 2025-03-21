package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class OesOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    /**
     * Order serial number
     */
    private String orderSn;

    private Long ticketId;

    private String ticketName;

    private String ticketPic;

    private Integer ticketQuantity;

    private BigDecimal ticketPrice;

    /**
     * Actual payment amount
     */
    private BigDecimal realAmount;

    /**
     * Coupon discount amount
     */
    private BigDecimal couponAmount;
}
