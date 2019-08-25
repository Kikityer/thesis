package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.POJO.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @ClassName StudentInfoRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/24 19:29
 * @Version 1.0.0
 **/
@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo,Integer>{


    /**
     * 根据学生的姓名进行模糊查询
     * @return
     */
    @Query(value = "select *from student_info where student_name like CONCAT('%',:keyName,'%')",nativeQuery=true)
    ArrayList<StudentInfo> findByStudentName(@Param("keyName") String keyName);


    /**
     * 根据学生的学号进行模糊查询
     * @param keyNum
     * @return
     */
    @Query(value = "select *from student_info where student_number like CONCAT('%',:keyNum,'%')",nativeQuery=true)
    ArrayList<StudentInfo> findByStudentNum(@Param("keyNum") String keyNum);


    /**
     * 根据楼栋名称和寝室号同时多条件查询
     * And的用法
     * @return
     */
    ArrayList<StudentInfo> findByBuildingNameAndDormNum(String buildingName,String dormNum);


    /***
     * 根据楼栋名称进行查询
     * @param buildingName
     * @return
     */
    ArrayList<StudentInfo> findByBuildingName(String buildingName);

    /**
     * 根据寝室号进行查询
     * @param dormNum
     * @return
     */
    ArrayList<StudentInfo> findByDormNum(String dormNum);


    /**
     * 根据锁id查找寝室中的学生信息
     * @param lockId
     * @return
     */
    ArrayList<StudentInfo> findByLockId(String lockId);
}
