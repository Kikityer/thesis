package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.UserScoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserScoreInfoRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/16 15:31
 * @Version 1.0.0
 **/
@Repository
public interface UserScoreInfoRepository extends JpaRepository<UserScoreInfo,Integer>{

}
