package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.POJO.BuildingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BuildingInfoRepository
 * @Description TODO  楼宇信息仓储类 jpa实现对象映射，在数据库中返回需要的数据
 * @Author Kikityer
 * @Date 2019/6/22 17:25
 * @Version 1.0.0
 **/

@Repository
public interface BuildingInfoRepository extends JpaRepository<BuildingInfo,Integer> {

    /**
     * 通过楼宇名称查找楼宇的信息返回一个PO对象
     * 楼宇名称是唯一的 所以查找的结果只有一个对象
     * @param buildingName
     * @return
     */
    BuildingInfo findByBuildingName(String buildingName);



    /**
     * jpa实现自定义注解
     * 实现通过宿管的名字进行模糊查找
     * @param keyName
     * @return
     */
    @Query(value = "select *from building_info where manager_name like CONCAT('%',:keyName,'%')",nativeQuery=true)
    ArrayList<BuildingInfo> findByManagerName(@Param("keyName") String keyName);



    /**
     * 拿到所有楼宇信息数据
     * @return
     */
    ArrayList<BuildingInfo> findAll();
}
