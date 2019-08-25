package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.POJO.SyncCommand;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName SyncCommandRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/26 11:38
 * @Version 1.0.0
 **/
public interface SyncCommandRepository extends JpaRepository<SyncCommand,String>{

    SyncCommand findByLockId(String lockId);
}
