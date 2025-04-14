package com.example.eventms.hub.domain;

import lombok.Getter;

@Getter
public enum ExchangeEnum {
    ORDER("order.exchange");

    private final String name;

    ExchangeEnum(String name) {
        this.name = name;
    }
}
