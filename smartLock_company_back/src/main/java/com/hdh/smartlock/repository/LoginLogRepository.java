package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.LoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LoginLogRepository
 * @Description TODO  //登陆日志仓储 DAO
 * @Author Kikityer
 * @Date 2019/5/30 14:28
 * @Version 1.0.0
 **/
@Repository
public interface LoginLogRepository extends JpaRepository<LoginLogEntity,Integer>{

    ArrayList<LoginLogEntity> findByLoginUsernameOrLoginPhoneNum(String username, String phoneNum);
}
