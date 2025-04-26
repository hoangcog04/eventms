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
@TableName("oes_refund_request")
@Schema(name = "OesRefundRequest", description = "")
public class OesRefundRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String orderSn;

    private Long eventId;

    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changed;

    @Schema(description = "Refund request name")
    private String fromName;

    @Schema(description = "Email used to create the refund request")
    private String fromEmail;

    private String message;

    private String reason;

    @Schema(description = "0->Default; 1->Offline; 2->Payments system")
    private Integer checkoutMethod;

    @Schema(description = "0->Email;1->Phone")
    private Integer deliveryMethod;

    @Schema(description = "Actual order payment amount")
    private BigDecimal orderPayAmount;

    @Schema(description = "0->Pending (default); 1->Refunding; 2->Processed; 3->Rejected; 4->Error")
    private Integer status;
}
