package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.POJO.UserScoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserScoreInfoRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/23 17:00
 * @Version 1.0.0
 **/
@Repository
public interface UserScoreInfoRepository extends JpaRepository<UserScoreInfo,Integer>{


}
