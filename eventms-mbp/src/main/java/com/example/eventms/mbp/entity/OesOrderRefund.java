package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2025-03
 */
@Getter
@Setter
@ToString
@TableName("oes_order_refund")
public class OesOrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long paymentCompanyId;

    private Long ticketId;

    private String orderSn;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String userUsername;

    /**
     * Refund amount
     */
    private BigDecimal refundAmount;

    private String refundName;

    private String refundEmail;

    /**
     * Status: 0->pending (default); 1->refunding; 2->completed; 3->rejected
     */
    private Integer status;

    private String ticketPic;

    private String ticketName;

    /**
     * Refund quantity
     */
    private Integer ticketCount;

    /**
     * Ticket price
     */
    private BigDecimal ticketPrice;

    /**
     * Actual payment price
     */
    private BigDecimal ticketRealPrice;

    private String reason;
}
