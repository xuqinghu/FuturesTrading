package com.xuantie.futures.network.exception;

/**
 * Created by Administrator on 2018/9/26.
 */

public class FailException extends Exception {
    private String code;
    private String message;

    private String status;

    public FailException(String message, String code, String status) {
        super(message);
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
