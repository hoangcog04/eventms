package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("ees_coupon")
@Schema(name = "EesCoupon", description = "")
public class EesCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    @Schema(description = "Quantity")
    private Integer quantity;

    @Schema(description = "Number of times this Discount has been used")
    private Integer useCount;

    @Schema(description = "0->amount off;1->percent off")
    private Integer useType;

    private BigDecimal amountOff;

    private Integer percentOff;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String code;

    private Long eventId;

    @Schema(description = "Separated by commas. Leave empty for all the tickets")
    private String ticketIds;
}
