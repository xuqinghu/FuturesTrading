package com.xuantie.futures.network.bean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/9/17.
 */

public class ResultModel<T> {
    public static final String REQ_SUCCESS = "1001";
    //401无权访问 token失效
    public static final List<String> REQ_TOKEN_ERROR = Arrays.asList("401");
    //1005    生成密钥参数错误  1006    生成密钥失败  1007    解密参数错误  1008    获取私钥失败  1009    解密失败
    public static final List<Integer> REQ_DECODE_ERROR = Arrays.asList(1005, 1006, 1007, 1008, 1009);
    private String token;
    private String forwardUrl;
    private String code;
    private String message;
    private T content;
    //异常
    private String status;

    private String getToken() {
        return token;
    }

    private void setToken(String token) {
        this.token = token;
    }

    private String getForwardUrl() {
        return forwardUrl;
    }

    private void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T data) {
        this.content = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
