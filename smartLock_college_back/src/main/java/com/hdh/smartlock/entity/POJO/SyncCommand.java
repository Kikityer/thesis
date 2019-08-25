package com.hdh.smartlock.entity.POJO;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName SyncCommand
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/26 11:36
 * @Version 1.0.0
 **/
@Entity
@Table(name = "sync_command")
@Data
public class SyncCommand {

    @Id
    @Column(name = "lock_id")
    private String lockId;

    @Column(name = "command")
    private String command;

    @Column(name = "info")
    private String info;
}
