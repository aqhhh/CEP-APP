package com.example.demo.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum UrlMethod {
    ALL, GET, POST, PUT, DELETE;

    @JsonValue
    public String getName() {
        return this.name();
    }

    @JsonCreator
    public static UrlMethod forName(String name) {
        for (UrlMethod method : values()) {
            if (Objects.equals(method.getName(), name)) {
                return method;
            }
        }
        return null;
    }
}
