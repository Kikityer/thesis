package com.hdh.smartlock.entity.VO;

import lombok.Data;

/**
 * @ClassName DormManagerInfoVO
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/24 17:24
 * @Version 1.0.0
 **/
@Data
public class DormManagerInfoVO {

    private Integer id;

    private String name;

    private Integer age;

    private String sexual;

    private String jobNum;

    private String jobNumPass;

    private String phoneNum;

    private String buildingName;

    private String role;

    private String registered;
}
