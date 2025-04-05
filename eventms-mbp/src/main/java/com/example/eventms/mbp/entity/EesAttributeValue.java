package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
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
@TableName("ees_attribute_value")
@Schema(name = "EesAttributeValue", description = "")
public class EesAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long eventId;

    private Long attributeCategoryId;

    private Long attributeId;

    @Schema(description = "The value of the attr's value list, separated by commas")
    private String value;
}
