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
public class EventAttrPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private AttrType type;

    private Data data;

    @Getter
    @Setter
    public static class AttrType implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long attributeCategoryId;

        private String attributeCategoryName;
    }

    @Getter
    @Setter
    public static class Data implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long attributeId;

        private String attributeName;

        @Schema(description = "The value of the attr's value list, separated by commas")
        private String value;
    }
}
