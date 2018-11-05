package com.xuantie.futures.network.exception;

/**
 * Created by Administrator on 2018/10/10.
 */

public class ServerException extends Exception{
    private int code;
    private String message;

    public ServerException(String message,int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
