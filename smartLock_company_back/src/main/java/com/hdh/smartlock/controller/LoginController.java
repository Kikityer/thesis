package com.hdh.smartlock.controller;

import com.hdh.smartlock.dto.LoginDTO;
import com.hdh.smartlock.entity.UserScoreInfo;
import com.hdh.smartlock.repository.UserScoreInfoRepository;
import com.hdh.smartlock.shiro.ReturnResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ClassName AdminInfoController
 * @Description TODO  登陆接口
 * @Author Kikityer
 * @Date 2019/5/6 20:00
 * @Version 1.0.0
 **/

@RestController
@RequestMapping(value = "/user")
public class LoginController {

    /**
     * 构造器注入
     */
    private final UserScoreInfoRepository scoreInfoRepository;
    @Autowired
    public LoginController(UserScoreInfoRepository scoreInfoRepository){
        this.scoreInfoRepository = scoreInfoRepository;
    }


    /**
     * 登陆认证操作
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ReturnResult loginTest(LoginDTO loginParam,HttpSession session){

        ReturnResult result = new ReturnResult();
        //1、检验验证码
        //session中的验证码
        if (loginParam.getCaptchaCode() != null){
            String sessionCaptcha = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if(!loginParam.getCaptchaCode().equals(sessionCaptcha)){
                result.setMsg("验证码错误");
                result.setCode(400);
                return result;
            }
        }
        //2、验证用户名和密码
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginParam.getUsername(), loginParam.getPassword(),loginParam.isRememberMe());
        try {
            // subject.login(token);
            // 此方法会间接调用我们自己实现的realm的doGetAuthenticationInfo，
            // 根据我们在数据库中获取的信息（存放在info中）和调用login方法时传递的AuthenticationToken中的信息对比。
            subject.login(token);
            result.setToken(subject.getSession().getId());
            result.setMsg("登录成功");
            result.setCode(200);
        } catch(Exception e){
            if(e instanceof AuthenticationException){
                result.setMsg("用户名或密码错误");
                result.setCode(400);
            }if(e instanceof LockedAccountException){
                result.setMsg("账号已被锁定,请联系管理员");
                result.setCode(400);
            }
        }
        return result;
    }

    /**
     * 实现评分接口
     */
    @RequestMapping(value = "/score",method = RequestMethod.POST)
    public ReturnResult giveScore(@RequestParam("score") String score){
        ReturnResult returnResult = new ReturnResult();
        UserScoreInfo scoreInfo = new UserScoreInfo();
        if (score.equals("1")){  //不满意
            scoreInfo.setStars("1");
            scoreInfo.setScore("20");
            scoreInfo.setSatisfaction("不满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            scoreInfoRepository.save(scoreInfo);
            returnResult.setCode(400);
            returnResult.setMsg("不满意");
        }else if (score.equals("2")){  //不满意
            scoreInfo.setStars("2");
            scoreInfo.setScore("40");
            scoreInfo.setSatisfaction("不满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            scoreInfoRepository.save(scoreInfo);
            returnResult.setCode(400);
            returnResult.setMsg("不满意");
        }else if (score.equals("3")){  //满意
            scoreInfo.setStars("3");
            scoreInfo.setScore("60");
            scoreInfo.setSatisfaction("满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            scoreInfoRepository.save(scoreInfo);
            returnResult.setCode(200);
            returnResult.setMsg("满意");
        }else if (score.equals("4")){  //很满意
            scoreInfo.setStars("4");
            scoreInfo.setScore("80");
            scoreInfo.setSatisfaction("很满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            scoreInfoRepository.save(scoreInfo);
            returnResult.setCode(200);
            returnResult.setMsg("很满意");
        }else {  //非常满意
            scoreInfo.setStars("5");
            scoreInfo.setScore("100");
            scoreInfo.setSatisfaction("非常满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            scoreInfoRepository.save(scoreInfo);
            returnResult.setCode(200);
            returnResult.setMsg("非常满意");
        }
        return returnResult;
    }
}
