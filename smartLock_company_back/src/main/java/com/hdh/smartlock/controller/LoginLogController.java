package com.hdh.smartlock.controller;

import com.hdh.smartlock.dto.LoginLogDTO;
import com.hdh.smartlock.dto.LoginLogInfoDTO;
import com.hdh.smartlock.entity.LoginLogEntity;
import com.hdh.smartlock.entity.UserRegisterEntity;
import com.hdh.smartlock.repository.LoginLogRepository;
import com.hdh.smartlock.repository.UserRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName LoginLogController
 * @Description TODO  登陆日志控制类
 * @Author Kikityer
 * @Date 2019/5/30 14:30
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/loginLog")
public class LoginLogController {


    //构造器注入
    private final LoginLogRepository loginLogRepository;
    private final UserRegisterRepository userRegisterRepository;
    @Autowired
    public LoginLogController(LoginLogRepository loginLogRepository,UserRegisterRepository userRegisterRepository){
        this.loginLogRepository = loginLogRepository;
        this.userRegisterRepository = userRegisterRepository;
    }


    /**
     * 接收前端登陆信息，存储为登陆日志
     * @param loginLogDTO
     */
    @RequestMapping(value = "/saveLog")
    public void savaLoginLog(@RequestBody LoginLogDTO loginLogDTO){

        //前台传入的用户名（可能为账号名或者手机号码）
        String str = loginLogDTO.getLoginUsername();
        UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUsername(str);
        //通过账号名找到了用户
        if (userRegisterEntity != null){
            String phoneNum = userRegisterEntity.getUserPhoneNum();
            Date now = new Date();
            LoginLogEntity loginLogEntity = new LoginLogEntity();
            loginLogEntity.setLoginUsername(str);
            loginLogEntity.setLoginPhoneNum(phoneNum);
            loginLogEntity.setLoginIP(loginLogDTO.getLoginIP());
            loginLogEntity.setLoginLocation(loginLogDTO.getLoginLocation());
            loginLogEntity.setLoginTime(now);
            loginLogRepository.save(loginLogEntity);
        }else {  //否则账号为手机号，通过手机号找到用户名存储到登陆日志表
            String username = userRegisterRepository.findByUserPhoneNum(str).getUsername();
            Date now = new Date();
            LoginLogEntity loginLogEntity = new LoginLogEntity();
            loginLogEntity.setLoginUsername(username);
            loginLogEntity.setLoginPhoneNum(str);
            loginLogEntity.setLoginIP(loginLogDTO.getLoginIP());
            loginLogEntity.setLoginLocation(loginLogDTO.getLoginLocation());
            loginLogEntity.setLoginTime(now);
            loginLogRepository.save(loginLogEntity);
        }
    }


    /**
     * 获取登陆日志接口
     * 只获取最新的六条登陆数据
     * @return
     */
    @RequestMapping(value = "/getLog")
    public List<LoginLogInfoDTO> getLoginLog(@RequestParam("username") String username){

        //返回数据装载容器
        ArrayList<LoginLogInfoDTO> backData = new ArrayList<>();

        ArrayList<LoginLogEntity> arrayList = loginLogRepository.findByLoginUsernameOrLoginPhoneNum(username,username);
        int len = arrayList.size();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (len <= 6){
            for(int i = len; i > 0; i--){
                LoginLogEntity loginLogEntity = arrayList.get(i-1);
                String sDate = sdf.format(loginLogEntity.getLoginTime());
                String location = loginLogEntity.getLoginLocation();
                String ip = loginLogEntity.getLoginIP();
                String allStr = location+" "+ip;
                LoginLogInfoDTO loginLogInfoDTO = new LoginLogInfoDTO();
                loginLogInfoDTO.setLoginTime(sDate);
                loginLogInfoDTO.setLoginLocation(allStr);
                backData.add(loginLogInfoDTO);
            }
        }else {
            for (int i = len; i > len-6; i--){
                LoginLogEntity loginLogEntity = arrayList.get(i-1);
                String sDate = sdf.format(loginLogEntity.getLoginTime());
                String location = loginLogEntity.getLoginLocation();
                String ip = loginLogEntity.getLoginIP();
                String allStr = location+"  "+"("+ip+")";
                LoginLogInfoDTO loginLogInfoDTO = new LoginLogInfoDTO();
                loginLogInfoDTO.setLoginTime(sDate);
                loginLogInfoDTO.setLoginLocation(allStr);
                backData.add(loginLogInfoDTO);
            }
        }
        return backData;

    }
}
