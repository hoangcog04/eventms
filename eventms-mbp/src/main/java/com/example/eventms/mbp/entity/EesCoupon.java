package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2025-04
 */
@Getter
@Setter
@ToString
@TableName("ees_coupon")
@Schema(name = "EesCoupon", description = "")
public class EesCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    @Schema(description = "Quantity")
    private Integer count;

    @Schema(description = "Number of times this Discount can be used")
    private Integer quantityAvailable;

    private Integer percentOff;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String code;

    @Schema(description = "Event ID for applicable discount. Leave empty for all events")
    private String couponEventId;
}
