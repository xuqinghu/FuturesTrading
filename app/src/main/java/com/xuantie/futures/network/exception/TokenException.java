package com.xuantie.futures.network.exception;

/**
 * Created by Administrator on 2018/9/17.
 */

public class TokenException extends Exception{
    private int status;
    private String message;

    public TokenException(String message, int status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }
}
