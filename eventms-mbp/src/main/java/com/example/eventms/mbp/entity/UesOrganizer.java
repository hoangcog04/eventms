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
@TableName("ues_organizer")
@Schema(name = "UesOrganizer", description = "")
public class UesOrganizer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String summary;

    private String email;

    private Integer eventCount;

    private String websiteUrl;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    private String avatar;

    private String bigPic;

    @Schema(description = "0->Disabled; 1->Enabled")
    private Integer status;
}
