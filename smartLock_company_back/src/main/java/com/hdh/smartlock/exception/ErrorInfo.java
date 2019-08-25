package com.hdh.smartlock.exception;

/**
 * @ClassName ErrorInfo
 * @Description 异常实体类
 * @Author Kikityer
 * @Date 2019/5/6 13:08
 * @Version 1.0.0
 **/
public class ErrorInfo<T> {

    public static final Integer OK = 0;
    public static final Integer ERROR = 1;

    private Integer code;//错误信息码，1表示发生异常
    private String message;//异常发生时系统打印的错误信息（默认为英文）  自定义异常时为new MyException（"..."）中的内容
    private String url; //请求路径
    private String data; //错误信息的解释（对英文的解释）

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
