package com.hdh.smartlock.entity.VO;

import lombok.Data;

/**
 * @ClassName StudentInfoVO
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/25 22:20
 * @Version 1.0.0
 **/
@Data
public class StudentInfoVO {

    private Integer id;

    private String studentName;

    private String studentNum;

    private String studentCardId;

    private String identityCardId;

    private String buildingName;

    private String dormNum;

    private String lockId;

    private String definedNum;

}
