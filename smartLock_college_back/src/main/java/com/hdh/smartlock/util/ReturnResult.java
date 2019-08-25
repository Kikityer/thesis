package com.hdh.smartlock.util;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName ReturnResult
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/23 16:23
 * @Version 1.0.0
 **/
public class ReturnResult implements Serializable {

    private int code = 0;
    private String msg = null;
    private Object token;
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public String toJsonString(){

        JSONObject json = new JSONObject((Map<String, Object>) this);
        return json.toString();
    }
}
