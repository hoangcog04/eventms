package com.example.eventms.organizer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class EventPublish implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean published;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
}
