package com.hdh.smartlock.controller;

import com.hdh.smartlock.util.ResultMsg;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * @ClassName LogOutController
 * @Description TODO  退出接口
 * @Author Kikityer
 * @Date 2019/5/7 13:39
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("/out")
public class LogOutController {

    @Value("${spring.redis.shiro.host}")
    private String host;
    @Value("${spring.redis.shiro.port}")
    private int port;


    @RequestMapping("/logout")
    public ResultMsg logout() {

        Logger logger = LoggerFactory.getLogger(getClass());

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = (String) session.getId();
        logger.info("sessionId{}", sessionId);
        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        Jedis jedis = new Jedis(shardInfo);
        long jedis_key = jedis.del("shiro:session:" + sessionId);
        logger.info("jedis_key{}", jedis_key);
        logger.info("--------数据已经删除--------");
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.success("成功退出登陆!");
        return resultMsg;
    }
}
