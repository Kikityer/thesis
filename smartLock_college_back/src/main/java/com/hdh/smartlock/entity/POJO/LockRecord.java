package com.hdh.smartlock.entity.POJO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName LockRecord
 * @Description TODO  门锁开锁记录信息实体类
 * @Author Kikityer
 * @Date 2019/6/22 15:38
 * @Version 1.0.0
 **/

@Entity
@Table(name = "lock_record")
@Data
public class LockRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "dorm_number")
    private String dormNum;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_number")
    private String studentNum;

    @Column(name = "unlock_measure")
    private String unlockMeasure;

    @Column(name = "unlock_time")
    private Date unlockTime;

}
