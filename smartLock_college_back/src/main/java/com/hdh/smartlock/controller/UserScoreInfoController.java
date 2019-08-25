package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.POJO.UserScoreInfo;
import com.hdh.smartlock.repository.UserScoreInfoRepository;
import com.hdh.smartlock.util.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName UserScoreInfoController
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/23 17:02
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/userScore")
public class UserScoreInfoController {


    /**
     * 构造器注入
     */
    @Autowired
    private UserScoreInfoRepository userScoreInfoRepository;
    UserScoreInfoController(UserScoreInfoRepository userScoreInfoRepositorys){
        this.userScoreInfoRepository = userScoreInfoRepositorys;
    }


    /**
     * 实现用户对本软件的评分接口
     */
    @RequestMapping(value = "/score",method = RequestMethod.POST)
    public ReturnResult giveScore(@RequestParam("score") String score) {
        ReturnResult returnResult = new ReturnResult();
        UserScoreInfo scoreInfo = new UserScoreInfo();
        if (score.equals("1")) {  //不满意
            scoreInfo.setStars("1");
            scoreInfo.setScore("20");
            scoreInfo.setSatisfaction("不满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            userScoreInfoRepository.save(scoreInfo);
            returnResult.setCode(400);
            returnResult.setMsg("不满意");
        } else if (score.equals("2")) {  //不满意
            scoreInfo.setStars("2");
            scoreInfo.setScore("40");
            scoreInfo.setSatisfaction("不满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            userScoreInfoRepository.save(scoreInfo);
            returnResult.setCode(400);
            returnResult.setMsg("不满意");
        } else if (score.equals("3")) {  //满意
            scoreInfo.setStars("3");
            scoreInfo.setScore("60");
            scoreInfo.setSatisfaction("满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            userScoreInfoRepository.save(scoreInfo);
            returnResult.setCode(200);
            returnResult.setMsg("满意");
        } else if (score.equals("4")) {  //很满意
            scoreInfo.setStars("4");
            scoreInfo.setScore("80");
            scoreInfo.setSatisfaction("很满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            userScoreInfoRepository.save(scoreInfo);
            returnResult.setCode(200);
            returnResult.setMsg("很满意");
        } else {  //非常满意
            scoreInfo.setStars("5");
            scoreInfo.setScore("100");
            scoreInfo.setSatisfaction("非常满意");
            Date date = new Date();
            scoreInfo.setMarkDate(date);
            userScoreInfoRepository.save(scoreInfo);
            returnResult.setCode(200);
            returnResult.setMsg("非常满意");
        }
        return returnResult;
    }
}
