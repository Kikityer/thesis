package com.hdh.smartlock.entity.DTO;

import lombok.Data;

/**
 * @ClassName DormManagerInfoDTO
 * @Description TODO  宿管信息传输对象（经PO去除掉员工密码信息）
 * @Author Kikityer
 * @Date 2019/6/22 15:34
 * @Version 1.0.0
 **/

@Data
public class DormManagerInfoDTO {

    private Integer id;

    private String name;

    private Integer age;

    private String sexual;

    private String jobNum;

    private String phoneNum;

    private String buildingName;

    private String role;

    private String registered;   //此员工是否已经注册
}
