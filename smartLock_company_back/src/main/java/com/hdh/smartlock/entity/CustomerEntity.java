package com.hdh.smartlock.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName CustomerEntity
 * @Description TODO  客户信息实体 对应数据库中购买智能锁的学校的相关信息
 * @Author Kikityer
 * @Date 2019/6/4 16:24
 * @Version 1.0.0
 **/
@Entity
@Table(name = "custom_info")
@Data
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "school_address")
    private String schoolAddress;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "purchase_batch")
    private String purchaseBatch;

    @Column(name = "batch_amount")
    private String batchAmount;

    @Column(name = "all_amount")
    private String allAmount;

}
