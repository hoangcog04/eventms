package com.example.eventms.organizer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class EventProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    private PropDto prop;

    private PropValueDto value;

    @Getter
    @Setter
    public static class PropDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String attributeCategoryName;

        private String name;

        @Schema(description = "Selection type: 0->no selection; 1->single; 2->multiple")
        private Integer selectType;

        @Schema(description = "Entry method: 0->no entry; 1->manual; 2->select from list")
        private Integer inputType;

        @Schema(description = "Value list, separated by commas")
        private String inputList;
    }

    @Getter
    @Setter
    public static class PropValueDto implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private Long attributeId;

        @Schema(description = "The value of the attr's value list, separated by commas")
        private String value;
    }
}
