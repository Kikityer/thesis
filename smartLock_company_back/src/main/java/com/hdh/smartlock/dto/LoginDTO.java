package com.hdh.smartlock.dto;

import java.io.Serializable;

/**
 * @ClassName LoginDTO
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/10 14:58
 * @Version 1.0.0
 **/
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 166457193110647497L;
    private String username;
    private String password;
    private String captchaCode;
    private boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", captchaCode='" + captchaCode + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
