package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.UserRegisterEntity;
import com.hdh.smartlock.repository.UserRegisterRepository;
import com.hdh.smartlock.shiro.ReturnResult;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RevisePasswordController
 * @Description TODO  修改密码控制类
 * @Author Kikityer
 * @Date 2019/6/4 14:33
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/revise")
public class RevisePasswordController {


    //构造器注入
    private final UserRegisterRepository userRegisterRepository;
    @Autowired
    public RevisePasswordController(UserRegisterRepository userRegisterRepository){
        this.userRegisterRepository = userRegisterRepository;
    }


    /**
     * 实现修改密码控制类
     * 判断旧密码的正确性
     * 若正确则更改旧密码为新密码
     * @param username
     * @param oldPass
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/password")
    public ReturnResult changePass(@RequestParam("username") String username,
                                   @RequestParam("oldPass") String oldPass,
                                   @RequestParam("newPass") String newPass){

        ReturnResult result = new ReturnResult();
        UserRegisterEntity userRegisterEntity = userRegisterRepository.findByUsernameOrUserPhoneNum(username,username);
        String oldPassword = userRegisterEntity.getUserPassword();
        String hashAlgorithmName = "md5";
        String credentials = oldPass;//密码
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes("156156bjkhi");//用户名作为盐值
        String passAfterMD5 = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations).toHex();
        if (oldPassword.equals(passAfterMD5)){  //旧密码验证正确
            if (!oldPass.equals(newPass)){  //新旧密码不能相等
                String credentials1 = newPass;//密码
                String  passAfterMD51 = new SimpleHash(hashAlgorithmName, credentials1, credentialsSalt, hashIterations).toHex();
                userRegisterEntity.setUserPassword(passAfterMD51);
                userRegisterRepository.save(userRegisterEntity);
                result.setCode(200);
                result.setMsg("success");
            }else {
                result.setCode(400);
                result.setMsg("same pass");
            }

        }else {   //旧密码验证错误
            result.setCode(400);
            result.setMsg("oldPass wrong");
        }

        return result;
    }
}
