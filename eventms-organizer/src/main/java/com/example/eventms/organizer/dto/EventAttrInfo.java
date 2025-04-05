package com.example.eventms.organizer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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
public class EventAttrInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private AttrCategoryDto attrCategory;

    private List<AttrDto> attrs;

    @Getter
    @Setter
    public static class AttrCategoryDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String name;
    }

    @Getter
    @Setter
    public static class AttrDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long attributeCategoryId;

        private String attributeCategoryName;

        private String name;

        @Schema(description = "Selection type: 0->no selection; 1->single; 2->multiple")
        private Integer selectType;

        @Schema(description = "Entry method: 0->no entry; 1->manual; 2->select from list")
        private Integer inputType;

        @Schema(description = "Value list, separated by commas")
        private String inputList;
    }
}
