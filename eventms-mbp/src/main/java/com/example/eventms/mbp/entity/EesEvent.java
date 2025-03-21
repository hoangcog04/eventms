package com.example.eventms.mbp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("ees_event")
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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @TableLogic
    private Integer deleted;

    /**
     * 0:Default; 1:Live; 2:Canceled
     */
    private Integer status;

    private Integer previewStatus;
}
