package com.hdh.smartlock.controller;

import com.hdh.smartlock.dto.RegisterDTO;
import com.hdh.smartlock.entity.UserRegisterEntity;
import com.hdh.smartlock.repository.UserRegisterRepository;
import com.hdh.smartlock.shiro.ReturnResult;
import com.hdh.smartlock.util.SendMSG;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @ClassName MockRegister
 * @Description TODO  新用户注册控制类
 * @Author Kikityer
 * @Date 2019/5/8 13:49
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/register")
public class UserRegisterController {

    //构造器注入注册DAO
    private final UserRegisterRepository userRegisterRepository;
    @Autowired
    public UserRegisterController(UserRegisterRepository userRegisterRepository){

        this.userRegisterRepository = userRegisterRepository;
    }

    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;


    /**
     * 一：验证手机号是否已经注册
     * 二：未注册的情况下发送短信
     *
     * 接收到前端表单中传过来的手机号码，即为用于接收短信的手机号
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/number")
    public ReturnResult sendMsg(@RequestParam("phoneNum") String phoneNum){

        SendMSG sendMSG = new SendMSG();
        ReturnResult resultback = new ReturnResult();
        UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUserPhoneNum(phoneNum);
        if (userRegisterEntity == null){  //手机号未注册
            sendMSG.send(phoneNum);
            JedisShardInfo shardInfo = new JedisShardInfo(host,port);
            Jedis jedis = new Jedis(shardInfo);
            jedis.setex("verifyCode",300,sendMSG.str); //将验证码存储到redis中，设置五分钟过期
            resultback.setCode(200);
            resultback.setMsg("send success");
        }else {
            resultback.setCode(400);
            resultback.setMsg("phoneNum used");
        }
        return resultback;
    }

    /**
     * 一：检查用户名是否有被使用
     * 二：校验手机验证码的正确性
     * 三：密码加密之后存储
     * @param registerDTO
     * @return
     */
    @Transactional   //默认运行时异常事务回滚
    @RequestMapping(value = "user",method = RequestMethod.POST)
    public ReturnResult Register(RegisterDTO registerDTO){
        ReturnResult result = new ReturnResult();
        UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUsername(registerDTO.getUsername());
        if (userRegisterEntity == null){  //用户名没有被使用过
            JedisShardInfo shardInfo = new JedisShardInfo(host,port);
            Jedis jedis = new Jedis(shardInfo);
            String code = jedis.get("verifyCode");
            if (code == null){
                result.setCode(400);
                result.setMsg("code expire");
            }else if (!code.equals(registerDTO.getVerifyCode())){
                result.setCode(400);
                result.setMsg("code not equal");
            }else {
                String hashAlgorithmName = "md5";
                String credentials = registerDTO.getPassword();//密码
                int hashIterations = 1024;
                ByteSource credentialsSalt = ByteSource.Util.bytes("156156bjkhi");//用户名作为盐值
                String  passAfterMD5 = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations).toHex();
                Date nowTime = new Date();
                UserRegisterEntity registerEntity = new UserRegisterEntity();
                registerEntity.setUsername(registerDTO.getUsername());
                registerEntity.setUserPhoneNum(registerDTO.getPhoneNum());
                registerEntity.setUserPassword(passAfterMD5);
                registerEntity.setUserCreateTime(nowTime);
                userRegisterRepository.save(registerEntity);
                result.setCode(200);
                result.setMsg("register success");
            }
        }else {
            result.setCode(400);
            result.setMsg("username used");
        }
        return result;
    }
}
