package com.example.eventms.hub.component;

import com.example.eventms.hub.service.IOesOrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Component
public class CancelOrderReceiver {
    IOesOrderService orderService;

    @RabbitListener(queues = "order.cancel", containerFactory = "retryContainerFactory")
    public void handleCancelOrder(Long orderId) {
        log.info("processing abandon orderId:{}", orderId);
        orderService.abandonOrder(orderId);
        log.info("successfully abandon orderId:{}", orderId);
    }

    @RabbitListener(queues = "order.cancel.parking", containerFactory = "retryContainerFactory")
    public void handleOrderParking(Long orderId) {
        log.info("logging parking orderId:{}", orderId);
        // pass
        log.info("successfully parking orderId:{}", orderId);
    }
}
