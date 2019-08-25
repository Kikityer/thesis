package com.hdh.smartlock.shiro;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @ClassName CustomFormAuthenticationFilter
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/9 16:58
 * @Version 1.0.0
 **/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);

    /*
     *  重写时注意事项：
     *      没有session,访问后台接口直接调用FormAuthenticationFilter.onAccessDeny()方法处理。
     *              1，当访问的是登陆接口的时候，调用AuthenticatingFilter.executeLogin()方法处理。
     *                    认证成功，调用 AuthenticatingFilter中 onLoginSuccess(token, subject, request, response);
     *                    认证失败，调用 AuthenticatingFilter中 onLoginFailure(token, e, request, response);
     *     						  在认证之前又开始进行了Token认证，所以要重写 createToken方法。
     *     		    2，当访问的是非登陆接口的时候，直接提示错误，需要先登陆。
     *
     *
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            log.info("--------------isLoginRequest--------------");
            if (this.isLoginSubmission(request, response)) {
                log.info("--------------isLoginSubmission--------------");
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                CustomAuthenticatingFilter ao = new CustomAuthenticatingFilter();
                return ao.executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        }else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            JSONObject json = new JSONObject();
            json.put("no-session", "未登录，无法访问该地址");
            out.println(json);
            out.flush();
            out.close();
            return false;
        }
    }


    @Override
    public AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        return createToken(username, password, request, response);
    }

}
