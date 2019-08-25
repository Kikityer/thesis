package com.hdh.smartlock.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName UserRegisterEntity
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/29 9:56
 * @Version 1.0.0
 **/
@Entity
@Table(name = "register_user")
@Data
public class UserRegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_phone_num")
    private String userPhoneNum;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_creat_time")
    private Date userCreateTime;
}
