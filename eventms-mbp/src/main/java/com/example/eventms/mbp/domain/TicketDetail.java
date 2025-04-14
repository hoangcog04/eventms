package com.example.eventms.mbp.domain;

import com.example.eventms.mbp.entity.EesTicket;
import com.example.eventms.mbp.entity.EesTicketStock;
import lombok.Getter;
import lombok.Setter;

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
public class TicketDetail extends EesTicket {

    EesTicketStock stock;
}
