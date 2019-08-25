package com.hdh.smartlock.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName LoginLogEntity
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/30 14:21
 * @Version 1.0.0
 **/
@Entity
@Table(name = "login_log")
@Data
public class LoginLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login_username")
    private String loginUsername;

    @Column(name = "login_phone_num")
    private String loginPhoneNum;

    @Column(name = "login_ip")
    private String loginIP;

    @Column(name = "login_location")
    private String loginLocation;

    @Column(name = "login_time")
    private Date loginTime;
}
