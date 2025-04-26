package com.example.eventms.hub.mapper;

import com.example.eventms.hub.dto.OrderResult;
import com.example.eventms.hub.dto.RefundRequestPayload;
import com.example.eventms.mbp.entity.OesOrder;
import com.example.eventms.mbp.entity.OesOrderAttendee;
import com.example.eventms.mbp.entity.OesRefundRequest;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vicendy04
 * @since 2025-03
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    OrderResult.OrderCost toOrderCost(OesOrder entity);

    OrderResult.OrderDto toOrderDto(OesOrder entity);

    OrderResult.AttendeeCost toAttendeeCost(OesOrderAttendee entity);

    OrderResult.AttendeeDto toAttendeeDto(OesOrderAttendee entity);

    OesRefundRequest toEntity(RefundRequestPayload dt);

    default OrderResult.OrderDto toResultOrder(OesOrder entity) {
        OrderResult.OrderDto order = toOrderDto(entity);
        order.setCost(toOrderCost(entity));
        return order;
    }

    default List<OrderResult.AttendeeDto> toResultAttendees(List<OesOrderAttendee> entities) {
        List<OrderResult.AttendeeDto> attendees = new ArrayList<>();
        for (var e : entities) {
            OrderResult.AttendeeDto attendee = toAttendeeDto(e);
            attendee.setCost(toAttendeeCost(e));
            attendees.add(attendee);
        }
        return attendees;
    }

    default OrderResult toOrderResult(OesOrder order, List<OesOrderAttendee> attendees) {
        OrderResult orderResult = new OrderResult();
        OrderResult.OrderDto orderDto = toResultOrder(order);

        List<OrderResult.AttendeeDto> attendeeDtos = toResultAttendees(attendees);
        orderResult.setOrder(orderDto);
        orderResult.setAttendees(attendeeDtos);

        return orderResult;
    }
}
