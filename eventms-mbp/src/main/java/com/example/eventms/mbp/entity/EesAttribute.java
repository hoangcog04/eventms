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
@TableName("ees_attribute")
@Schema(name = "EesAttribute", description = "")
public class EesAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long attributeCategoryId;

    private String name;

    @Schema(description = "Selection type: 0->no selection; 1->single; 2->multiple")
    private Integer selectType;

    @Schema(description = "Entry method: 0->no entry; 1->manual; 2->select from list")
    private Integer inputType;

    @Schema(description = "Value list, separated by commas")
    private String inputList;
}
