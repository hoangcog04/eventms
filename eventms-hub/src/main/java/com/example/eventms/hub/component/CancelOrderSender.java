package com.example.eventms.hub.component;

import com.example.eventms.hub.domain.QueueEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import static com.example.eventms.hub.domain.QueueEnum.*;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class CancelOrderSender {
    AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId) {
        amqpTemplate.convertAndSend(QUEUE_TTL_ORDER_CANCEL.getExchange(),
                QUEUE_TTL_ORDER_CANCEL.getRouteKey(),
                orderId);
        log.info("send orderId:{}", orderId);
    }
}
