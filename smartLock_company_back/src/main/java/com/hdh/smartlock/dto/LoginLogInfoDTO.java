package com.hdh.smartlock.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LoginLogInfoDTO
 * @Description TODO  将登陆日志信息组装成前台需要的数据格式 只有登陆时间和地点
 * @Author Kikityer
 * @Date 2019/5/30 21:12
 * @Version 1.0.0
 **/
@Data
public class LoginLogInfoDTO implements Serializable{

    private String loginTime;

    private String loginLocation;
}
