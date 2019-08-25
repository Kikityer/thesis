package com.hdh.smartlock.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName UserScoreInfo
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/5/16 15:18
 * @Version 1.0.0
 **/
@Entity
@Table(name = "company_user_score")
public class UserScoreInfo implements Serializable {

    private static final long serialVersionUID = 7416373978493379166L;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Date getMarkDate() {
        return markDate;
    }

    public void setMarkDate(Date markDate) {
        this.markDate = markDate;
    }

    @Override
    public String toString() {
        return "UserScoreInfo{" +
                "id=" + id +
                ", stars='" + stars + '\'' +
                ", score='" + score + '\'' +
                ", satisfacation='" + satisfaction + '\'' +
                ", markDate=" + markDate +
                '}';
    }
}
