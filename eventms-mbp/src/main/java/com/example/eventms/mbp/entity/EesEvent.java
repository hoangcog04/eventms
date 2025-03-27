package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@TableName("ees_event")
@Schema(name = "EesEvent", description = "")
public class EesEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long organizerId;

    private Long venueId;

    private String title;

    private String summary;

    private String pic;

    private String albumPics;

    private LocalDateTime date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String currencyCode;

    @TableLogic
    private Integer deleted;

    @Schema(description = "0:Draft; 1:Live; 2:Started, 3: Ended, 4.Canceled")
    private Integer status;

    @Schema(description = "Set event capacity to prevent overselling")
    private Integer capacity;

    private Integer previewStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime changed;

    private LocalDateTime publishTime;
}
