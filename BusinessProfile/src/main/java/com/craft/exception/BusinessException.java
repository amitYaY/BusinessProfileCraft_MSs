package com.craft.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusinessException extends Exception {

    private int statusCode;

    private Error error;

    public BusinessException(int statusCode, Error error, String message, Throwable ex) {
        super(message, ex);
        this.statusCode = statusCode;
        this.error = error;
    }

}
