package com.xuantie.futures.network.exception;

/**
 * Created by Administrator on 2018/10/10.
 */

public class TokenException1 extends Exception{
    private int status;
    private String message;

    public TokenException1(String message, int status) {
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
