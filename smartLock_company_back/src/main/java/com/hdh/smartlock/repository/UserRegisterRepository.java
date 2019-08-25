package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.UserRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRegisterRepository
 * @Description TODO  用户注册实体类仓储  DAO
 * @Author Kikityer
 * @Date 2019/5/29 10:03
 * @Version 1.0.0
 **/
@Repository
public interface UserRegisterRepository extends JpaRepository<UserRegisterEntity,Integer>{

    //根据用户名查找用户接口
    UserRegisterEntity findByUsername(String username);


    //根据手机号查找用户接口
    UserRegisterEntity findByUserPhoneNum(String phoneNum);


    //登陆时可以使用用户名或者手机号
    UserRegisterEntity findByUsernameOrUserPhoneNum(String username,String phoneNum);

}
