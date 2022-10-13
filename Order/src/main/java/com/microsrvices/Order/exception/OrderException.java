package com.microsrvices.Order.exception;

import lombok.Data;

@Data
public class OrderException extends RuntimeException{

    private String errorCode;
    private int status;

    public OrderException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}