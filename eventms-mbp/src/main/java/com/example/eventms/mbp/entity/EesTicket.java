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
@TableName("ees_ticket")
@Schema(name = "EesTicket", description = "")
public class EesTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long eventId;

    private String name;

    private String summary;

    private String pic;

    private BigDecimal price;

    @Schema(description = "Total available number of this ticket")
    private Integer quantityTotal;

    @Schema(description = "The number of sold tickets")
    private Integer quantitySold;

    @Schema(description = "Total capacity of this ticket")
    private Integer capacity;

    @Schema(description = "Maximum number per order (blank uses default value 10)")
    private Integer maxQuantityPerOrder;

    private Integer sorting;

    private Integer isFree;

    private Integer isHidden;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changed;
}
