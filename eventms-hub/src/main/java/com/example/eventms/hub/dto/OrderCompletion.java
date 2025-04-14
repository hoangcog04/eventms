package com.example.eventms.hub.dto;

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
public class OrderCompletion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "0->Email;1->Phone")
    private Integer deliveryMethod;
}
