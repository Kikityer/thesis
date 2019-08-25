package com.hdh.smartlock.dto;

import lombok.Data;

/**
 * @ClassName RegisterDTO
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/29 10:14
 * @Version 1.0.0
 **/
@Data
public class RegisterDTO {

    private String username;

    private String phoneNum;

    private String verifyCode;

    private String password;
}
