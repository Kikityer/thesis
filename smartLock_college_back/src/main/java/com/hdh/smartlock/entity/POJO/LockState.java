package com.hdh.smartlock.entity.POJO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName LockState
 * @Description TODO  门锁状态实体类
 * @Author Kikityer
 * @Date 2019/6/22 15:44
 * @Version 1.0.0
 **/
@Entity
@Table(name = "lock_state")
@Data
public class LockState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "dorm_number")
    private String dormNum;

    @Column(name = "lock_id")
    private String lockId;

    @Column(name = "lock_battery")
    private Integer lockBattery;

    //1 弱     2 中      3强
    @Column(name = "lock_signal")
    private Integer lockSignal;

    @Column(name = "lock_state_time")
    private Date stateTime;


}
