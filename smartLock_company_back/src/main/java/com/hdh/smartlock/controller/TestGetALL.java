package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.UserRegisterEntity;
import com.hdh.smartlock.repository.UserRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName TestGetALL
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/29 14:24
 * @Version 1.0.0
 **/
@RestController

public class TestGetALL {

    private final UserRegisterRepository userRegisterRepository;
    @Autowired
    TestGetALL(UserRegisterRepository userRegisterRepository){

        this.userRegisterRepository = userRegisterRepository;
    }
    @RequestMapping(value = "/getAll")
    public List<UserRegisterEntity> find(){

        return userRegisterRepository.findAll();
    }
}
