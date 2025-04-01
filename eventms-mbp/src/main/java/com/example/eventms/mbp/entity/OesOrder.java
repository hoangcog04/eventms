package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@TableName("oes_order")
@Schema(name = "OesOrder", description = "")
public class OesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long eventId;

    @Schema(description = "Order serial number")
    private String orderSn;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @Schema(description = "Order owner name")
    private String ownerName;

    @Schema(description = "Total order amount")
    private BigDecimal totalAmount;

    @Schema(description = "Actual payment amount")
    private BigDecimal payAmount;

    @Schema(description = "Coupon discount amount")
    private BigDecimal couponAmount;

    @Schema(description = "0->Default; 1->Offline; 2->Payments system")
    private Integer checkoutMethod;

    @Schema(description = "0->Pending;1->Completed;2->Canceled;3->Refunded")
    private Integer status;

    @Schema(description = "0->Email;1->Phone")
    private Integer deliveryMethod;

    @Schema(description = "Email for invoice (optional)")
    private String billReceiverEmail;

    @Schema(description = "Attendee name (required)")
    private String receiverName;

    @TableLogic
    private Integer deleted;

    private LocalDateTime paymentTime;

    private LocalDateTime deliveryTime;
}
