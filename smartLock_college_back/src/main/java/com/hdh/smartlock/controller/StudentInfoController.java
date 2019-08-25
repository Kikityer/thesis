package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.POJO.BuildingInfo;
import com.hdh.smartlock.entity.POJO.StudentInfo;
import com.hdh.smartlock.entity.POJO.SyncCommand;
import com.hdh.smartlock.entity.VO.StudentInfoVO;
import com.hdh.smartlock.exception.MyException;
import com.hdh.smartlock.repository.BuildingInfoRepository;
import com.hdh.smartlock.repository.StudentInfoRepository;
import com.hdh.smartlock.repository.SyncCommandRepository;
import com.hdh.smartlock.util.ExcelUtil;
import com.hdh.smartlock.util.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName StudentInfoController
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/24 21:26
 * @Version 1.0.0
 **/

@RestController
@RequestMapping(value = "/student")
public class StudentInfoController {


    /**
     * 构造器注入
     */
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    private BuildingInfoRepository buildingInfoRepository;
    private SyncCommandRepository syncCommandRepository;
    StudentInfoController(StudentInfoRepository studentInfoRepository,
                          BuildingInfoRepository buildingInfoRepository,
                          SyncCommandRepository syncCommandRepository){

        this.studentInfoRepository = studentInfoRepository;
        this.buildingInfoRepository = buildingInfoRepository;
        this.syncCommandRepository = syncCommandRepository;
    }


    /**
     * 1：初始化表格信息（默认当前页为1，每页显示10条数据）
     * 2：分页查找信息（输入：当前页数和每页显示的条数）
     *
     * 分页查找
     *
     * @return
     */
    @RequestMapping(value = "/getStudentInfo")
    public Page<StudentInfo> getAllSchoolInfo(@RequestParam("pageSize") Integer pageSize,
                                              @RequestParam("currentPageNum") Integer currentPageNum){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(currentPageNum-1, pageSize, Sort.Direction.ASC, "id");

        return studentInfoRepository.findAll(pageable);
    }


    /**
     * 根据学生的名字进行模糊查询
     * @param studentName
     * @return
     */
    @RequestMapping(value = "/findByStudentName")
    public ArrayList<StudentInfo> findByStudentName(@RequestParam("studentName") String studentName) throws MyException {

        ArrayList<StudentInfo> arrayList;
        arrayList = studentInfoRepository.findByStudentName(studentName);
        if (arrayList.size() == 0 ){
            throw new MyException("no name");
        }else {
            return arrayList;
        }
    }


    /**
     * 根据学生的学号进行模糊查询
     * @param studentNum
     * @return
     */
    @RequestMapping(value = "/findByStudentNum")
    public ArrayList<StudentInfo> findByStudentNum(@RequestParam("studentNum") String studentNum) throws MyException {

        ArrayList<StudentInfo> arrayList;
        arrayList = studentInfoRepository.findByStudentNum(studentNum);
        if (arrayList.size() == 0 ){
            throw new MyException("no name");
        }else {
            return arrayList;
        }
    }


    /**
     * 根据楼宇名称进行查询
     * @param buildingName
     * @return
     */
    @RequestMapping(value = "/findByBuildingName")
    public ArrayList<StudentInfo> findByBuildingName(@RequestParam("buildingName") String buildingName) throws MyException {

        ArrayList<StudentInfo> arrayList;
        arrayList = studentInfoRepository.findByBuildingName(buildingName);
        if (arrayList.size() == 0 ){
            throw new MyException("no name");
        }else {
            return arrayList;
        }

    }


    /**
     * 根据寝室号进行查询
     * @param dormNumber
     * @return
     */
    @RequestMapping(value = "/findByDormNum")
    public ArrayList<StudentInfo> findByDormNum(@RequestParam("dormNumber") String dormNumber) throws MyException {

        ArrayList<StudentInfo> arrayList;
        arrayList = studentInfoRepository.findByDormNum(dormNumber);
        if (arrayList.size() == 0 ){
            throw new MyException("no name");
        }else {
            return arrayList;
        }
    }


    /**
     * 根据楼宇名称和寝室号进行多条件查询
     * and
     * @param buildingName
     * @param dormNumber
     * @return
     */
    @RequestMapping(value = "/findByLocation")
    public ArrayList<StudentInfo> findByLocation(@RequestParam("buildingName") String buildingName,
                                                 @RequestParam("dormNumber") String dormNumber) throws MyException {

        ArrayList<StudentInfo> arrayList;
        arrayList = studentInfoRepository.findByBuildingNameAndDormNum(buildingName,dormNumber);
        if (arrayList.size() == 0 ){
            throw new MyException("no name");
        }else {
            return arrayList;
        }

    }


    /**
     * 前端获取自定义编号，将已经使用的编号去除，提供能够选择使用的选项返回至前台
     * @return
     */
    @RequestMapping(value = "/getDefinedNum")
    public ArrayList<String> getDefinedNum(@RequestParam("buildingName") String buildingName,
                                           @RequestParam("dormNumber") String dormNumber){

        ArrayList<String> arrayList = new ArrayList<>();  //装载返回前台的数据
        ArrayList<String> arrayList2 = new ArrayList<>();  //装载已经被使用的编号

        BuildingInfo buildingInfo = buildingInfoRepository.findByBuildingName(buildingName);
        Integer capacity = buildingInfo.getDormCapacity();  //获取寝室容量

        ArrayList<StudentInfo> arrayList1 = studentInfoRepository.findByBuildingNameAndDormNum(buildingName,dormNumber);
        if (arrayList1.size() != 0){
            for(StudentInfo studentInfo: arrayList1){
                arrayList2.add(studentInfo.getDefinedNum());
            }
            for (int i = 1;i < capacity+1; i++){
                if (!arrayList2.contains(Integer.toString(i))){
                    arrayList.add(Integer.toString(i));
                }
            }
        }else {
            for (int i = 1;i < capacity+1; i++){
                arrayList.add(Integer.toString(i));
            }
        }
        if (arrayList.size() == 0){
            arrayList.add("此寝室已满，请更换寝室");
        }
        return arrayList;
    }




    /**
     * 获取前端批量提交的学生信息excel表格
     * @param file
     */
    @RequestMapping(value = "/getExcelInfo")
    @Transactional
    public void getExcelInfo(MultipartFile file){

        long t1 = System.currentTimeMillis();
        List<StudentInfo> list = ExcelUtil.readExcel("", StudentInfo.class, file);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
        SyncCommand syncCommand = new SyncCommand();
        for (StudentInfo studentInfo : list){
            studentInfoRepository.save(studentInfo);
            String SyncResult = findByLockId(studentInfo.getLockId());  //调用上面定义的函数
            syncCommand.setLockId(studentInfo.getLockId());
            syncCommand.setInfo(SyncResult);
            syncCommandRepository.save(syncCommand);
        }
    }



    /**
     * 根据锁id在学生信息表中查找学生相关信息的函数
     */
    public String findByLockId(String lockId){

        StringBuilder stringBuilder = new StringBuilder();
        String number;
        String cardID;
        String identificationID;
        ArrayList<StudentInfo> arrayList = studentInfoRepository.findByLockId(lockId);
        if (arrayList.size() != 0){  //通过锁id能找到
            for (int i = 0 ; i < arrayList.size() ; i++){
                number = "0"+arrayList.get(i).getDefinedNum();
                cardID = arrayList.get(i).getStudentCardId();
                identificationID = arrayList.get(i).getIdentityCardId();
                stringBuilder.append(number+cardID+identificationID);
            }
        }else {
            stringBuilder.append("");
        }
        return stringBuilder.toString();
    }
    /**
     * 新增一个学生信息
     * 同更新的一个学生的效果一样，将信息保存在student_info表中，同时同步同一个寝室的所有学生的信息到sync_command表中
     * @return
     */
    @RequestMapping(value = "/addStudentInfo")
    @Transactional
    public ReturnResult addStudentInfo(StudentInfoVO studentInfoVO){

        ReturnResult result = new ReturnResult();

        //保存新增加学生的相关信息到student_info表中
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentName(studentInfoVO.getStudentName());
        studentInfo.setStudentNum(studentInfoVO.getStudentNum());
        studentInfo.setStudentCardId(studentInfoVO.getStudentCardId());
        studentInfo.setIdentityCardId(studentInfoVO.getIdentityCardId());
        studentInfo.setBuildingName(studentInfoVO.getBuildingName());
        studentInfo.setDormNum(studentInfoVO.getDormNum());
        studentInfo.setLockId(studentInfoVO.getLockId());
        studentInfo.setDefinedNum(studentInfoVO.getDefinedNum());
        studentInfoRepository.save(studentInfo);

        //将新增加的学生信息及同一个寝室同学的相关信息同步到sync_command表中
        SyncCommand syncCommand = syncCommandRepository.findByLockId(studentInfoVO.getLockId());
        if (syncCommand == null){
            SyncCommand syncCommand1 = new SyncCommand();
            String SyncResult = findByLockId(studentInfoVO.getLockId());  //调用上面定义的函数
            syncCommand1.setLockId(studentInfoVO.getLockId());
            syncCommand1.setInfo(SyncResult);
            syncCommandRepository.save(syncCommand1);
        }else {
            String SyncResult = findByLockId(studentInfoVO.getLockId());  //调用上面定义的函数
            syncCommand.setInfo(SyncResult);
            syncCommandRepository.save(syncCommand);
        }
        result.setCode(200);
        result.setMsg("add success");
        return result;
    }


    /**
     * 编辑学生信息
     * 更新学生信息操作
     * 更新学生的信息同增加一个学生的信息效果相同
     * 只能更新学生的学生卡id和身份证id
     * @return
     */
    @RequestMapping(value = "/editStudentInfo")
    @Transactional
    public ReturnResult editStudentInfo(@RequestParam("id") Integer id,
                                        @RequestParam("studentCardId") String studentCardId,
                                        @RequestParam("identityCardId") String identityCardId){

        ReturnResult result = new ReturnResult();

        //更新一个学生的相关信息到student_info表中
        StudentInfo studentInfo = studentInfoRepository.findById(id).orElse(null); //根据id查找到学生信息
        String lockId = studentInfo.getLockId(); //获取本学生对应的锁id
        studentInfo.setStudentCardId(studentCardId);
        studentInfo.setIdentityCardId(identityCardId);
        studentInfoRepository.save(studentInfo);

        SyncCommand syncCommand = syncCommandRepository.findByLockId(lockId);
        String SyncResult = findByLockId(lockId);  //调用上面定义的函数
        syncCommand.setInfo(SyncResult); //将返回的字符串覆盖之前的info字段内的信息
        syncCommandRepository.save(syncCommand);

        result.setCode(200);
        result.setMsg("add success");
        return result;
    }


    /**
     * 删除一个学生信息
     * @return
     */
    @RequestMapping(value = "/deleteOne")
    @Transactional
    public ReturnResult deleteOne(@RequestParam("id")  Integer id){
        ReturnResult result = new ReturnResult();

        StudentInfo studentInfo = studentInfoRepository.findById(id).orElse(null);
        String lockId = studentInfo.getLockId();  //获得删除的学生寝室的锁id
        String definedNum = "0"+studentInfo.getDefinedNum(); //获得删除学生的用户编号
        studentInfoRepository.deleteById(id);

        String SyncResult = findByLockId(lockId);  //调用上面定义的函数
        SyncCommand syncCommand = syncCommandRepository.findByLockId(lockId);
        if (syncCommand.getCommand() == null){  //删除之前判断command字段是否为空，若为空则直接插入
            syncCommand.setCommand(definedNum);
        }else {                                //若不为空则直接拼接
            String beforeCommand = syncCommand.getCommand();
            String afterCommand = beforeCommand + definedNum;
            syncCommand.setCommand(afterCommand);
        }
        syncCommand.setInfo(SyncResult);
        syncCommandRepository.save(syncCommand);
        result.setCode(200);
        result.setMsg("delete ok");
        return result;
    }


    /**
     * 批量删除高校客户信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteMany")
    @Transactional
    public ReturnResult deleteMany(@RequestParam("ids") Integer ids[]){
        ReturnResult result = new ReturnResult();
        for (int i = 0; i < ids.length; i++){
            StudentInfo studentInfo = studentInfoRepository.findById(ids[i]).orElse(null);
            String lockId = studentInfo.getLockId();  //获得删除的学生寝室的锁id
            String definedNum = "0"+studentInfo.getDefinedNum(); //获得删除学生的用户编号
            studentInfoRepository.deleteById(ids[i]);

            String SyncResult = findByLockId(lockId);  //调用上面定义的函数
            SyncCommand syncCommand = syncCommandRepository.findByLockId(lockId);
            if (syncCommand.getCommand() == null){  //删除之前判断command字段是否为空，若为空则直接插入
                syncCommand.setCommand(definedNum);
            }else {                                //若不为空则直接拼接
                String beforeCommand = syncCommand.getCommand();
                String afterCommand = beforeCommand + definedNum;
                syncCommand.setCommand(afterCommand);
            }
            syncCommand.setInfo(SyncResult);
            syncCommandRepository.save(syncCommand);
        }
        result.setCode(200);
        result.setMsg("delete ok");
        return result;
    }
}
