package com.hdh.smartlock.entity.POJO;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName BuildingInfo
 * @Description TODO  楼宇信息实体类
 * @Author Kikityer
 * @Date 2019/6/22 15:16
 * @Version 1.0.0
 **/

@Entity
@Table(name = "building_info")
@Data
public class BuildingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "lock_number")
    private Integer lockNum;

    @Column(name = "for_whom")
    private String forWhom;

    @Column(name = "dorm_capacity")
    private Integer dormCapacity;

    @Column(name = "manager_name")
    private String managerName;

}
