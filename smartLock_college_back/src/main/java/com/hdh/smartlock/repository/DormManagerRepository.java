package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.POJO.DormManagerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

/**
 * @ClassName DormManagerRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/24 14:19
 * @Version 1.0.0
 **/
public interface DormManagerRepository extends JpaRepository<DormManagerInfo,Integer>{

    /**
     * 查找所有的宿管信息
     * @return
     */
    ArrayList<DormManagerInfo> findAll();


    /**
     * 根据宿管的名字进行查找模糊查询
     * @param keyName
     * @return
     */
    @Query(value = "select *from dorm_manager_info where staff_name like CONCAT('%',:keyName,'%')",nativeQuery=true)
    ArrayList<DormManagerInfo> findByName(@Param("keyName") String keyName);


    /**
     * 根据宿管的工号进行模糊查询
     * @param keyNum
     * @return
     */
    @Query(value = "select *from dorm_manager_info where staff_job_number like CONCAT('%',:keyNum,'%')",nativeQuery=true)
    ArrayList<DormManagerInfo> findByJobNum(@Param("keyNum") String keyNum);
}
