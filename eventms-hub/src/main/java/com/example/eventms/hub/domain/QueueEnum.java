package com.example.eventms.hub.domain;

import lombok.Getter;

@Getter
public enum QueueEnum {
    QUEUE_ORDER_CANCEL(ExchangeEnum.ORDER.getName(), "order.cancel", "order.cancel"),

    QUEUE_TTL_ORDER_CANCEL(ExchangeEnum.ORDER.getName(), "order.cancel.ttl", "order.cancel.ttl"),

    QUEUE_PARKING(ExchangeEnum.ORDER.getName(), "order.cancel.parking", "order.cancel.parking");

    private final String exchange;
    private final String name;
    private final String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
