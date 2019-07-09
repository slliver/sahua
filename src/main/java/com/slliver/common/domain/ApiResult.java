package com.slliver.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 14:24
 * @version: 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> extends Result<T>{

    private static final long serialVersionUID = -5994958521886429114L;

    private String code;

    private String message;

    private boolean status;

    private T data;

    public ApiResult() {
    }

    public ApiResult(String code, String message, boolean status, T data) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
