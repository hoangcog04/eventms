package com.example.eventms.organizer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
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
 * @since 2025-04
 */
@Getter
@Setter
public class EventContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String purpose;

    private List<Module> modules;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Widget> widgets;

    @Getter
    @Setter
    public static class Module implements Serializable {

        private static final long serialVersionUID = 1L;

        @Schema(description = "Module type. Currently support text, image")
        private ModuleType type;

        private JsonNode data;
    }

    @Getter
    @Setter
    public static class Widget implements Serializable {

        private static final long serialVersionUID = 1L;

        @Schema(description = "Widget type. We currently support agenda and faqs")
        private WidgetType type;

        private JsonNode data;
    }

    public enum ModuleType {
        TEXT, IMAGE,
    }

    public enum WidgetType {
        AGENDA, FAQS,
    }

    public static class Slide implements Serializable {

        private static final long serialVersionUID = 1L;

        private String pic;

        private String albumPics;
    }
}
