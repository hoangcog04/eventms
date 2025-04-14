package com.example.eventms.hub.component;

import com.example.eventms.hub.service.IOesOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class CancelOrderReceiver {
    IOesOrderService orderService;

    @RabbitListener(queues = "order.cancel")
    @RabbitHandler
    public void handle(Long orderId) {
        orderService.abandonOrder(orderId);
        log.info("processing abandon orderId:{}", orderId);
    }
}
