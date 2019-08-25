package com.hdh.smartlock.controller;

import com.hdh.smartlock.exception.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ShiroCoreController
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/10 9:19
 * @Version 1.0.0
 **/
@RestController
public class ShiroCoreController {
    @GetMapping("/loginUnAuth")
    public String loginUnAuth() throws  MyException {

       throw new MyException("no login");   //没有登陆或者登陆超时
    }




    @GetMapping("/authorUnAuth")
    public String authorUnAuth() throws MyException {

        throw new MyException("no auth！");  //没有权限访问
    }
}
