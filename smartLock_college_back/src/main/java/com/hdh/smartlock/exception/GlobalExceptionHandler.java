package com.hdh.smartlock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO 全局异常处理类
 * @Author Kikityer
 * @Date 2019/6/22 17:19
 * @Version 1.0.0
 **/

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger logger  = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    //自定义异常
    @ExceptionHandler(value = MyException.class)
    public ErrorInfo<String> myExceptionHandler(HttpServletRequest req, MyException e){
        logger.error(e.getMessage());
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(2);
        r.setUrl(req.getRequestURL().toString());
        r.setData("自定义异常");
        return r;
    }

    //空指针异常
    @ExceptionHandler(value = NullPointerException.class)
    public ErrorInfo<String> nullPointerExceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("空指针异常："+e.getMessage());
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setUrl(req.getRequestURL().toString());
        r.setData("空指针异常");
        return r;
    }


    //数组下标越界
    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    public ErrorInfo<String> ArrayIndexOutOfBoundsException(HttpServletRequest req, ArrayIndexOutOfBoundsException e){
        logger.error("数组下标越界异常，错误下标为："+e.getMessage());
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setUrl(req.getRequestURL().toString());
        r.setData("数组下标越界异常");
        return r;
    }

    //session过期异常或者在没有登陆的情况下访问需要认证的接口
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ErrorInfo<String> ExpiredSessionException(HttpServletRequest req, HttpRequestMethodNotSupportedException  e){
        logger.error("session过期异常或者在没有登陆的情况下访问需要认证的接口："+e.getMessage());
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setUrl(req.getRequestURL().toString());
        r.setData("请登陆后再访问！");
        return r;
    }

    //其他异常
    @ExceptionHandler(value = Exception.class)
    public ErrorInfo<String> otherExceptionHandler(HttpServletRequest req, Exception e){
        logger.error("其他异常："+e.getMessage());
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setUrl(req.getRequestURL().toString());
        r.setData("其他异常");
        return r;
    }
}
