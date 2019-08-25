package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.UserRegisterEntity;
import com.hdh.smartlock.repository.UserRegisterRepository;
import com.hdh.smartlock.shiro.ReturnResult;
import com.hdh.smartlock.util.SendMSG;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import javax.servlet.http.HttpSession;

/**
 * @ClassName ForgetPassController
 * @Description TODO  忘记密码（验证身份、设置新密码）
 * @Author Kikityer
 * @Date 2019/5/29 14:58
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/forgetPass")
public class ForgetPassController {

    //构造器注入
    private final UserRegisterRepository userRegisterRepository;
    @Autowired
    public ForgetPassController(UserRegisterRepository userRegisterRepository){
        this.userRegisterRepository = userRegisterRepository;
    }

    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;


    ReturnResult result = new ReturnResult();
    /**
     * 第一步：填写手机号，获取kaptcha验证码
     * @return
     */
    @RequestMapping(value = "/kaptchaVerify")
    public ReturnResult kaptchaVerify(@RequestParam("phoneNum") String phoneNum,
                                      @RequestParam("captchaCode") String captchaCode,
                                      HttpSession session){

        UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUserPhoneNum(phoneNum);
        if(userRegisterEntity != null){
            String sessionCaptcha = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if(!captchaCode.equals(sessionCaptcha)){
                result.setCode(400);
                result.setMsg("wrong code");
            }else {
                result.setCode(200);
                result.setMsg("next");
            }
        }else {
            result.setCode(400);
            result.setMsg("unregister phoneNum");

        }
        return result;
    }


    /**
     *第二步：获取手机验证码，然后验证身份
     * @param phoneNum
     * @return
     */

    //获取手机验证码
    @RequestMapping(value = "/getmsg")
    public ReturnResult getMsg(@RequestParam("phoneNum") String phoneNum){

        SendMSG sendMSG = new SendMSG();
        sendMSG.send(phoneNum);
        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        Jedis jedis = new Jedis(shardInfo);
        jedis.setex("Code",300,sendMSG.str); //将验证码存储到redis中，设置五分钟过期
        result.setCode(200);
        result.setMsg("send success");
        return result;
    }

    //验证身份
    @RequestMapping(value = "verifyID")
    public ReturnResult verifyID(@RequestParam("phoneNum") String phoneNum,
                                 @RequestParam("verifyCode") String verifyCode){

        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        Jedis jedis = new Jedis(shardInfo);
        String code = jedis.get("Code");
        if (code == null){
            result.setCode(400);
            result.setMsg("code expire");
        }else if (!verifyCode.equals(code)){
            result.setCode(400);
            result.setMsg("code not equal");
        }else {
            result.setCode(200);
            result.setMsg("success");
        }
        return result;
    }


    /**
     * 第三步：设置新密码
     * @param phoneNum
     * @param password
     * @return
     */
    @RequestMapping(value = "/repass")
    public ReturnResult repass(@RequestParam("phoneNum") String phoneNum,
                               @RequestParam("password") String password){

        UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUserPhoneNum(phoneNum);
        String hashAlgorithmName = "md5";
        String credentials = password;//密码
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes("156156bjkhi");//用户名作为盐值
        String  passAfterMD5 = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations).toHex();
        userRegisterEntity.setUserPassword(passAfterMD5);
        userRegisterRepository.save(userRegisterEntity);
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

}
