package com.hdh.smartlock.entity.POJO;

import com.hdh.smartlock.annotation.ExcelColumn;
import lombok.Data;

import javax.persistence.Column;

/**
 * @ClassName ExcelStudent
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/25 20:17
 * @Version 1.0.0
 **/
@Data
public class ExcelStudent {

    @ExcelColumn(value = "姓名",col = 1)
    private String studentName;

    @ExcelColumn(value = "学号",col = 2)
    private String studentNum;

    @ExcelColumn(value = "学生卡ID",col = 3)
    private String studentCardId;

    @ExcelColumn(value = "身份证ID",col = 4)
    private String identityCardId;

    @ExcelColumn(value = "楼栋名称",col = 5)
    private String buildingName;

    @ExcelColumn(value = "寝室号",col = 6)
    private String dormNum;

    @ExcelColumn(value = "锁ID",col = 7)
    private String lockId;

    @ExcelColumn(value = "编号",col = 8)
    private String definedNum;  //学生在宿舍中自定义的编号   1 2 3 4
}
