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
@TableName("ees_checkout_setting")
@Schema(name = "EesCheckoutSetting", description = "")
public class EesCheckoutSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long eventId;

    private String countryCode;

    private String currencyCode;

    @Schema(description = "0->Default; 1->Offline; 2->Payments system")
    private Integer checkoutMethod;

    @Schema(description = "Left empty if checkout method is not offline")
    private String offlineNote;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changed;
}
