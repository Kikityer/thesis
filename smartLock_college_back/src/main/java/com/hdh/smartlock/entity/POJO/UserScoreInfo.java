package com.hdh.smartlock.entity.POJO;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName UserScoreInfo
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/23 16:56
 * @Version 1.0.0
 **/

@Entity
@Table(name = "college_user_score")
@Data
public class UserScoreInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "stars")
    private String stars;

    @Column(name = "score")
    private String score;

    @Column(name = "satisfaction")
    private String satisfaction;

    @Column(name = "mark_date")
    private Date markDate;
}
