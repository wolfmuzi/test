package com.iuling.comm.json;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class AjaxJsonResponse implements Serializable {
    private boolean success = true;// 是否成功
    private String errorCode = "-1";//错误代码
    private String msg = "操作成功";// 提示信息
    private Object body;//封装json的map
    public AjaxJsonResponse(){}
    public AjaxJsonResponse(boolean success,String errorCode,String msg,Object body) {
        this.success = success;
        this.errorCode = errorCode;
        this.msg = msg;
        this.body = body;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
