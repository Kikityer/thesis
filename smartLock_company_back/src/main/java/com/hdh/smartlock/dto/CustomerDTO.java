package com.hdh.smartlock.dto;

import lombok.Data;

/**
 * @ClassName CustomerDTO
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/11 11:34
 * @Version 1.0.0
 **/
@Data
public class CustomerDTO {


    private Integer id;

    private String schoolName;

    private String schoolAddress;

    private String purchaseDate;

    private String purchaseBatch;

    private String batchAmount;

    private String allAmount;
}
