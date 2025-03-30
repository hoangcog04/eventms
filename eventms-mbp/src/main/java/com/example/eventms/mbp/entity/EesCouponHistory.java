package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
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
@TableName("ees_coupon_history")
@Schema(name = "EesCouponHistory", description = "")
public class EesCouponHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long couponId;

    private String couponCode;

    private Long userId;

    @Schema(description = "Redeemer name")
    private String redeemerName;

    @Schema(description = "Use time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    private Long orderId;

    private String orderSn;
}
