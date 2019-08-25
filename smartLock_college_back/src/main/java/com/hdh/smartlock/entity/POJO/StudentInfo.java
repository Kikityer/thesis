package com.hdh.smartlock.entity.POJO;

import com.hdh.smartlock.annotation.ExcelColumn;
import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName StudentInfo
 * @Description TODO 学生信息实体类
 * @Author Kikityer
 * @Date 2019/6/22 15:53
 * @Version 1.0.0
 **/
@Entity
@Table(name = "student_info")
@Data
public class StudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ExcelColumn(value = "姓名",col = 1)
    @Column(name = "student_name")
    private String studentName;

    @ExcelColumn(value = "学号",col = 2)
    @Column(name = "student_number")
    private String studentNum;

    @ExcelColumn(value = "学生卡ID",col = 3)
    @Column(name = "student_card_id")
    private String studentCardId;

    @ExcelColumn(value = "身份证ID",col = 4)
    @Column(name = "identity_card_id")
    private String identityCardId;

    @ExcelColumn(value = "楼栋名称",col = 5)
    @Column(name = "building_name")
    private String buildingName;

    @ExcelColumn(value = "寝室号",col = 6)
    @Column(name = "dorm_number")
    private String dormNum;

    @ExcelColumn(value = "锁ID",col = 7)
    @Column(name = "lock_id")
    private String lockId;

    @ExcelColumn(value = "编号",col = 8)
    @Column(name = "user_defined_number")
    private String definedNum;  //学生在宿舍中自定义的编号   1 2 3 4

}
