package com.hdh.smartlock.entity.POJO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName UserRegister
 * @Description TODO   POJO 简单朴素类 操作数据库中的数据表，产生PO（持久化对象）
 *              TODO   注册用户实体类
 * @Author Kikityer
 * @Date 2019/6/22 15:07
 * @Version 1.0.0
 **/

@Entity
@Table(name = "register_user")
@Data
public class UserRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_job_num")
    private String userJobNum;

    @Column(name = "user_phone_num")
    private String userPhoneNum;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_create_time")
    private Date userCreateTime;

}
