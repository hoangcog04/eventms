package com.example.eventms.hub.dto;

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
public class RefundApplyPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    private RefundInfo refundInfo;

    private List<RequestItem> requestItems;

    @Getter
    @Setter
    public static class RefundInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String reason;
    }

    @Getter
    @Setter
    public static class RequestItem implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long attendeeId;

        @Schema(description = "Quantity refunded")
        private Integer refundQty;
    }
}
