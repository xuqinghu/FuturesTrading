package com.xuantie.futures.network.bean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/10/10.
 */

public class ResultModel1<T> {
    public static final int REQ_SUCCESS = 200;
    //401无权访问 token失效
    public static final List<Integer> REQ_TOKEN_ERROR = Arrays.asList(401);
    //1005    生成密钥参数错误  1006    生成密钥失败  1007    解密参数错误  1008    获取私钥失败  1009    解密失败
    public static final List<Integer> REQ_DECODE_ERROR = Arrays.asList(1005, 1006, 1007, 1008, 1009);

    //状态码
    private int status;
    private String code;
    private String message;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
