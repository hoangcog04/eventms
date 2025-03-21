package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2025-03
 */
@Getter
@Setter
@ToString
@TableName("ees_attribute")
public class EesAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long attributeCategoryId;

    private String name;

    /**
     * Selection type: 0->default; 1->single; 2->multiple
     */
    private Integer selectType;

    /**
     * Entry method: 0->manual; 1->select from list
     */
    private Integer inputType;

    /**
     * Value list, separated by commas
     */
    private String inputList;
}
