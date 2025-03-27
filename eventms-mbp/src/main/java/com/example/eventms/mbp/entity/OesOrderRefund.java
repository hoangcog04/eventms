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
 * @since 2025-03
 */
@Getter
@Setter
@ToString
@TableName("oes_order_refund")
@Schema(name = "OesOrderRefund", description = "")
public class OesOrderRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long paymentCompanyId;

    private Long ticketId;

    private String orderSn;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @Schema(description = "Refund requester name")
    private String refundName;

    @Schema(description = "Refund amount")
    private BigDecimal refundAmount;

    private String refundEmail;

    @Schema(description = "Status: 0->pending (default); 1->refunding; 2->completed; 3->rejected")
    private Integer status;

    private String ticketPic;

    private String ticketName;

    @Schema(description = "Refund quantity")
    private Integer ticketQuantity;

    @Schema(description = "Ticket price")
    private BigDecimal ticketPrice;

    @Schema(description = "Actual payment price")
    private BigDecimal ticketRealPrice;

    private String reason;
}
