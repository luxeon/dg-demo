package com.dg.demo.input;

import com.dg.demo.exception.OrderTypeNotFoundException;

import java.util.stream.Stream;

public enum OrderType {

    DESC, ASC;

    public static OrderType get(String value) {
        return Stream.of(values()).filter(type -> type.name().toLowerCase().equals(value.toLowerCase()))
                .findFirst().orElseThrow(OrderTypeNotFoundException::new);
    }
}
