package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.POJO.LockRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

/**
 * @ClassName LockRecordRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/7/1 10:57
 * @Version 1.0.0
 **/
@Repository
public interface LockRecordRepository extends JpaRepository<LockRecord,Integer>{

    /**
     * 获取一周内开锁记录信息
     * @param old
     * @param now
     * @return
     */
    @Query(value = "select * from lock_record where unlock_time between :old and :now",nativeQuery = true)
    ArrayList<LockRecord> getWeekRecordInfo(@Param("now") Date now, @Param("old") Date old);


    /**
     * 根据楼栋名称进行查找
     * 返回一周以内的数据
     * @param now
     * @param old
     * @param building
     * @return
     */
    @Query(value = "select * from lock_record where unlock_time between :old and :now and building_name = :building",nativeQuery = true)
    ArrayList<LockRecord> findByBuildingName(@Param("now") Date now, @Param("old") Date old, @Param("building") String building);


    /**
     * 根据寝室号称进行查找
     * 返回一周以内的数据
     * @param now
     * @param old
     * @param num
     * @return
     */
    @Query(value = "select * from lock_record where unlock_time between :old and :now and dorm_number = :num",nativeQuery = true)
    ArrayList<LockRecord> findByDormNum(@Param("now") Date now, @Param("old") Date old, @Param("num") String num);


    /**
     * 多条件查询
     * and
     * 根据楼栋名和寝室号称进行查找
     * 返回一周以内的数据
     * @param now
     * @param old
     * @param building
     * @param num
     * @return
     */
    @Query(value = "select * from lock_record where unlock_time between :old and :now and building_name = :building and dorm_number = :num",nativeQuery = true)
    ArrayList<LockRecord> findByBuildingNameAndDormNum(@Param("now") Date now, @Param("old") Date old,@Param("building") String building,  @Param("num") String num);




    /**
     * 根据学号进行查询
     * 不区分大小写
     * @param num
     * @return
     */
    @Query(value = "select * from lock_record where student_number = :num",nativeQuery = true)
    ArrayList<LockRecord> findByStudentNum(@Param("num") String num);


    /**
     * 根据开锁的时间进行查询
     * 模糊查询
     * @param keyName
     * @return
     */
    @Query(value = "select *from lock_record where unlock_time like CONCAT('%',:keyName,'%')",nativeQuery=true)
    ArrayList<LockRecord> findByUnlockTime(@Param("keyName") String keyName);


}
