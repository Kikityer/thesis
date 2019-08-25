package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.UserRegisterEntity;
import com.hdh.smartlock.repository.UserRegisterRepository;
import com.hdh.smartlock.shiro.ReturnResult;
import com.hdh.smartlock.util.SendMSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * @ClassName RevisePhoneNumController
 * @Description TODO 修改手机号控制
 * @Author Kikityert
 * @Date 2019/5/31 15:38
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/revise")
public class RevisePhoneNumController {


    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;

    //构造器注入
    private final UserRegisterRepository userRegisterRepository;
    @Autowired
    RevisePhoneNumController(UserRegisterRepository userRegisterRepository){
        this.userRegisterRepository = userRegisterRepository;
    }


    /**
     * 传入当前登陆用户账号，找到该账号对应的手机号码
     * @return
     */
    ReturnResult result = new ReturnResult();
    @RequestMapping(value = "/phoneNum")
    public ReturnResult findPhoneNum(@RequestParam("username") String username){
        UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUsernameOrUserPhoneNum(username,username);
        String phoneNum = userRegisterEntity.getUserPhoneNum();
        result.setCode(200);
        result.setMsg(phoneNum);
        return result;
    }


    /**
     * 获取旧号码短信验证码
     * @return
     */
    @RequestMapping(value = "/getOldCode")
    public ReturnResult sendMsgToOldPhone(@RequestParam("phoneNum") String phoneNum){

        SendMSG sendMSG = new SendMSG();
        sendMSG.send(phoneNum);
        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        Jedis jedis = new Jedis(shardInfo);
        jedis.setex("oldPhone",300,sendMSG.str); //将验证码存储到redis中，设置五分钟过期
        result.setCode(200);
        result.setMsg("send success");
        return result;
    }


    /**
     * 验证旧号码接收的验证码的正确性
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "/verifyOldNum")
    public ReturnResult verifyOldPhone(@RequestParam("verifyCode") String verifyCode){

        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        Jedis jedis = new Jedis(shardInfo);
        String code = jedis.get("oldPhone");
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
     * 获取新号码短信验证码
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/getNewCode")
    public ReturnResult sendMsgToNewPhone(@RequestParam("phoneNum") String phoneNum){

        SendMSG sendMSG = new SendMSG();
        sendMSG.send(phoneNum);
        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        Jedis jedis = new Jedis(shardInfo);
        jedis.setex("newPhone",300,sendMSG.str); //将验证码存储到redis中，设置五分钟过期
        result.setCode(200);
        result.setMsg("send success");
        return result;
    }


    /**
     * 验证新号码接收的验证码的正确性
     * @param oldphoneNum
     * @param newPhoneNum
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "/verifyNewNum")
    public ReturnResult verifyNewPhone(@RequestParam("oldPhoneNum") String oldphoneNum,
                                       @RequestParam("newPhoneNum") String newPhoneNum,
                                       @RequestParam("verifyCode") String verifyCode){


        //先验证手机验证码是否正确，若正确的话直接将新的号码保存
        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        Jedis jedis = new Jedis(shardInfo);
        String code = jedis.get("newPhone");
        if (code == null){
            result.setCode(400);
            result.setMsg("code expire");
        }else if (!verifyCode.equals(code)){
            result.setCode(400);
            result.setMsg("code not equal");
        }else {
            UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUserPhoneNum(oldphoneNum);
            userRegisterEntity.setUserPhoneNum(newPhoneNum);
            userRegisterRepository.save(userRegisterEntity);
            result.setCode(200);
            result.setMsg("success");
        }
        return result;
    }

}
