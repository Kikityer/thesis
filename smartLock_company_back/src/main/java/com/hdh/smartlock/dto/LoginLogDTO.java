package com.hdh.smartlock.dto;

import lombok.Data;

/**
 * @ClassName LoginLogDTO
 * @Description TODO  登陆日志接收对象
 * @Author Kikityer
 * @Date 2019/5/30 14:34
 * @Version 1.0.0
 **/
@Data
public class LoginLogDTO {

    private String loginUsername;

    private String loginIP;

    private String loginLocation;
}
