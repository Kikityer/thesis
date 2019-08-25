package com.hdh.smartlock.entity.POJO;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName DormManagerInfo
 * @Description TODO  宿管信息实体类
 * @Author Kikityer
 * @Date 2019/6/22 15:21
 * @Version 1.0.0
 **/
@Entity
@Table(name = "dorm_manager_info")
@Data
public class DormManagerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "staff_name")
    private String name;

    @Column(name = "staff_age")
    private Integer age;

    @Column(name = "staff_sexual")
    private String sexual;

    @Column(name = "staff_job_number")
    private String jobNum;

    @Column(name = "staff_password")
    private String jobNumPass;

    @Column(name = "staff_phone_number")
    private String phoneNum;

    @Column(name = "staff_building_name")
    private String buildingName;

    @Column(name = "staff_role")
    private String role;

    @Column(name = "have_registered")
    private String registered;   //此员工是否已经注册
}
