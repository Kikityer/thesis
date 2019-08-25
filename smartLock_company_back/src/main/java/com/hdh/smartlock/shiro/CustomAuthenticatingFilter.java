package com.hdh.smartlock.shiro;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName CustomAuthenticatingFilter
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/9 15:10
 * @Version 1.0.0
 **/
public class CustomAuthenticatingFilter extends AuthenticatingFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticatingFilter.class);

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        log.info("--------开始执行登陆认证---------");
        CustomFormAuthenticationFilter formAuthen = new CustomFormAuthenticationFilter();
        AuthenticationToken token = formAuthen.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
            log.info("----------核对登陆信息----------------");
            Subject subject = getSubject(request, response);
            subject.login(token);
            return this.onLoginSuccess(token, subject, request, response); //登陆成功
        } catch (AuthenticationException e) {

            return this.onLoginFailure(token, e, request, response);  //登陆失败
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        log.info("--------登陆认证成功------");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        return true;

    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.info("--------登陆认证失败------");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JSONObject json = new JSONObject();
        String exc = e.getClass().getName();
        if(exc.equals(UnknownAccountException.class.getName())){
            json.put("fail", "账户不存在");
        }
        if(exc.equals(IncorrectCredentialsException.class.getName())){
            json.put("fail", "密码不正确");
        }
        out.println(json);
        out.flush();
        out.close();
        return false;
    }




    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
