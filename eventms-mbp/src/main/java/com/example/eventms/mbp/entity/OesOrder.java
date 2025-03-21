package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("oes_order")
public class OesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * Order serial number
     */
    private String orderSn;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String userUsername;

    /**
     * Total order amount
     */
    private BigDecimal totalAmount;

    /**
     * Actual payment amount
     */
    private BigDecimal payAmount;

    /**
     * Coupon discount amount
     */
    private BigDecimal couponAmount;

    /**
     * 0->Unpaid;1->...
     */
    private Integer payType;

    /**
     * 0:Pending;1:Completed;2:Canceled;3:Refunded
     */
    private Integer status;

    /**
     * 0:Email;1:Phone
     */
    private Integer deliveryType;

    /**
     * Email for invoice (optional)
     */
    private String billReceiverEmail;

    /**
     * Attendee name (required)
     */
    private String receiverName;

    @TableLogic
    private Integer deleted;

    private LocalDateTime paymentTime;

    private LocalDateTime deliveryTime;
}
