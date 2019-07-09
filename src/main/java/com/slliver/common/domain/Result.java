package com.slliver.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * @author: slliver
 * @date: 2018/3/5 14:21
 * @version: 1.0
 * @description 业务类返回结果对象，适用于返回多个返回值
 * 如操作正确时返回数据，操作错误时返回错误信息
 * <p>
 * 如果需要简单返回true，直接返回 new Result() 即可，因为success的默认值是true；
 * 如果使用带errorMsg的构造函数则success强制改为false
 * <p>
 * 将success的值设置为true时，强制将errorMsg的值清空
 * 设置errorMsg的值时，强制将success的值改为false
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1727976638700084078L;

    private T data;
    private List<T> list;
    private boolean success = true;
    private String errorMsg;

    public Result() {
    }

    public Result(String errorMsg) {
        this.setErrorMsg(errorMsg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean getSuccess() {
        return isSuccess();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        if (success) this.setErrorMsg(null);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        this.success = false;
    }

}
