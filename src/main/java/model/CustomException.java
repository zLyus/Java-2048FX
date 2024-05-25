package model;

import java.io.Serializable;

public class CustomException extends RuntimeException implements Serializable {

    public CustomException(String msg) {
        super(msg);
    }
}
