package com.example.java2048fx;

public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        throw new RuntimeException(msg);
    }
}
