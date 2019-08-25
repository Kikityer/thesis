package com.hdh.smartlock.entity.VO;

import lombok.Data;

/**
 * @ClassName BuildingInfoVO
 * @Description TODO  楼宇信息类（VO 接收前端界面传输来的参数对象）
 * @Author Kikityer
 * @Date 2019/6/23 16:25
 * @Version 1.0.0
 **/
@Data
public class BuildingInfoVO {

    private Integer id;

    private String buildingName;

    private Integer lockNum;

    private String forWhom;

    private Integer dormCapacity;

    private String managerName;
}
