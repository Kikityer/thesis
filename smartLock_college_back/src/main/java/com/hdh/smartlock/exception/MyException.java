package com.hdh.smartlock.exception;

/**
 * @ClassName MyException
 * @Description TODO  自定义异常类  在逻辑处理类中直接抛出自定义的异常信息
 * @Author Kikityer
 * @Date 2019/6/22 17:20
 * @Version 1.0.0
 **/
public class MyException extends Exception{

    public MyException(String msg){

        super(msg);
    }
}
