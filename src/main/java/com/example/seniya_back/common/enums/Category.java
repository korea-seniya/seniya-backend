package com.example.seniya_back.common.enums;

import com.example.seniya_back.common.constants.ResponseMessage;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    SLEEP, REHABILITATION, EXERCISE, PSYCHOLOGY;

    @JsonCreator
    public static Category from(String value) {
        try {
            return Category.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ResponseMessage.FORMAT_ERROR);
        }
    }
}
