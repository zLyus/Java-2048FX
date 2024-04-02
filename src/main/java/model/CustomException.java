package model;

public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        throw new RuntimeException(msg);
    }
}
