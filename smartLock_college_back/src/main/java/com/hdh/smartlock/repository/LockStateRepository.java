package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.POJO.LockState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;

/**
 * @ClassName LockStateRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/26 14:59
 * @Version 1.0.0
 **/
public interface LockStateRepository extends JpaRepository<LockState,Integer>{


    /**
     * 获取24小时内的锁状态信息
     * @param old
     * @param now
     * @return
     */
    @Query(value = "select * from lock_state where lock_state_time between :old and :now",nativeQuery = true)
    ArrayList<LockState> getDayStateInfo(@Param("now") Date now, @Param("old") Date old);



    /**
     * 根据楼栋名称进行锁状态查询
     * 并且只返回24小时内的数据
     * @param old
     * @param now
     * @param building
     * @return
     */
    @Query(value = "select * from lock_state where lock_state_time between :old and :now and building_name = :building",nativeQuery = true)
    ArrayList<LockState> findByBuildingName(@Param("now") Date now, @Param("old") Date old, @Param("building") String building);


    /**
     * 根据寝室号进行锁状态查询
     * 并且只返回24小时内的数据
     * @param now
     * @param old
     * @param num
     * @return
     */
    @Query(value = "select * from lock_state where lock_state_time between :old and :now and dorm_number = :num",nativeQuery = true)
    ArrayList<LockState> findByDormNum(@Param("now") Date now, @Param("old") Date old, @Param("num") String num);


    /**
     * 根据楼栋名称和寝室号共同查询一个门锁的状态信息
     * 只返回24小时内的数据
     * @param now
     * @param old
     * @param building
     * @param num
     * @return
     */
    @Query(value = "select * from lock_state where lock_state_time between :old and :now and building_name = :building and dorm_number = :num",nativeQuery = true)
    ArrayList<LockState> findByBuildingNameAndDormNum(@Param("now") Date now, @Param("old") Date old,@Param("building") String building,  @Param("num") String num);


    /**
     * 根据锁id进行门锁状态的查询
     * 只返回24小时内的数据
     * @param now
     * @param old
     * @param lockId
     * @return
     */
    @Query(value = "select * from lock_state where lock_state_time between :old and :now and lock_id = :lockId",nativeQuery = true)
    ArrayList<LockState> findByLockId(@Param("now") Date now, @Param("old") Date old, @Param("lockId") String lockId);


    /**
     * 根据日期进行模糊查询
     * @param keyName
     * @return
     */
    @Query(value = "select *from lock_state where lock_state_time like CONCAT('%',:keyName,'%')",nativeQuery=true)
    ArrayList<LockState> findByStateTime(@Param("keyName") String keyName);
}
