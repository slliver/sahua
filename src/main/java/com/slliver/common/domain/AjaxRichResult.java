package com.slliver.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slliver.common.Constant;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 14:23
 * @version: 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxRichResult extends AjaxResult {

    private static final long serialVersionUID = -5050728059793418515L;

    /**
     * 返回码值,默认值Constants.FAI
     */
    private int res = Constant.FAIL;

    /**错误号，将service中可能发生的错误进行归类编号*/
    private String code;

    /**提示消息，在服务端生成，消息体保存在资源文件中*/
    private String message;

    private boolean status;

    private Object data;

    /**成功操作的记录数量*/
    private int resCount;

    /**
     * check错误项目名称
     * 包括项目单独check错误，业务check错误
     */
    private String checkErrorName;

    public AjaxRichResult(){
    }

    public AjaxRichResult(String code, String message, boolean status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public AjaxRichResult(String code, String message, boolean status, Object data) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getResCount() {
        return resCount;
    }

    public void setResCount(int resCount) {
        this.resCount = resCount;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    /**
     * 设置成功值
     * @param obj  设置对象
     * @param resMsg  设置码值解析
     */
    public void setSucceed(Object obj,String resMsg){
        this.setMessage(resMsg);
        this.setSucceed(obj);
        this.setStatus(true);
    }
    /**
     * 设置成功值
     * @param obj 设置对象
     */
    public void setSucceed(Object obj){
        this.data = obj;
        this.setRes(Constant.SUCCEED);
        this.setStatus(true);
    }
    /**
     * 设置成功值
     * @param resMsg 返回码值解析
     */
    public void setSucceedMsg(String resMsg){
        this.setRes(Constant.SUCCEED);
        this.setMessage(resMsg);
        this.setStatus(true);
    }

    /**
     * 设置失败值
     * @param resMsg 返回码值解析
     */
    public void setFailMsg(String resMsg){
        this.data = null;
        this.setRes(Constant.FAIL);
        this.setMessage(resMsg);
    }

    public String getCheckErrorName() {
        return checkErrorName;
    }

    public void setCheckErrorName(String checkErrorName) {
        this.checkErrorName = checkErrorName;
    }

    /**
     * 出错时返回出错对象以及错误信息
     * @Description: 出错时返回出错对象以及错误信息
     * @param result 出错信息列表
     * @param messageService 消息服务
     */
    /**
    public void setCheckError(BindingResult result, WebMessageService messageService) {
        this.data = null;
        this.setRes(Constant.CHECKERROR);
        List<FieldError> errorList = result.getFieldErrors();
        if (errorList != null && errorList.size() > 0) {
            FieldError fieldError = errorList.get(0);
            String fieldName = fieldError.getField();
            String messageKey = fieldError.getDefaultMessage();
            String errMessage = messageService.getMessage(messageKey.replace("{", "").replace("}", ""), fieldError.getArguments());
            this.setCheckErrorName(fieldName);
            this.setMessage(errMessage);
        }
    }
    **/

    /**
     * 出错时返回出错对象以及错误信息
     * @Description: 出错时返回出错对象以及错误信息
     * @param result 出错信息列表
     * @param messageService 消息服务
     */
    public void setCheckError(String fieldName, String errorMessage) {
        this.data = null;
        this.setRes(Constant.CHECKERROR);
        this.setCheckErrorName(fieldName);
        this.setMessage(errorMessage);
    }
}
