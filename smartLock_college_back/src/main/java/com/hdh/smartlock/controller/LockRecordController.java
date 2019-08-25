package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.POJO.LockRecord;
import com.hdh.smartlock.entity.POJO.LockState;
import com.hdh.smartlock.exception.MyException;
import com.hdh.smartlock.repository.LockRecordRepository;
import com.hdh.smartlock.repository.LockStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName LockRecordController
 * @Description TODO  开锁记录信息 查询等操作控制类
 * @Author Kikityer
 * @Date 2019/7/1 11:06
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/lockRecord")
public class LockRecordController {


    /**
     * 构造器注入
     */
    @Autowired
    private LockRecordRepository lockRecordRepository;
    private LockStateRepository lockStateRepository;
    LockRecordController(LockRecordRepository lockRecordRepository,LockStateRepository lockStateRepository){
        this.lockRecordRepository = lockRecordRepository;
        this.lockStateRepository = lockStateRepository;
    }


    /**
     * 前端表格信息初始化，获取一周以内的开锁记录信息
     * 表格展示时间由当前时间到七天前（需要倒序处理）
     * @return
     */
    @RequestMapping(value = "/getWeekRecord")
    public ArrayList<LockRecord> getWeekRecord(){
        ArrayList<LockRecord> arrayList = new ArrayList<>();   //装载返回的数据
        Date current = new Date();
        Date old = new Date(current.getTime() - 7 * 24 * 60 * 60 * 1000);
        ArrayList<LockRecord> arrayList1 = lockRecordRepository.getWeekRecordInfo(current,old);
        for (int i = arrayList1.size(); i > 0; i--){
            arrayList.add(arrayList1.get(i - 1));     //按照从最新日期到七天前的日期返回
        }
        return arrayList;
    }



    /**
     * 根据楼栋名称查找锁状态信息
     * 返回一周内的信息
     * 表格展示时间有当前时间到七天前的时间（需要倒序处理）
     * @param buildingName
     * @return
     */
    @RequestMapping(value = "/findByBuildingName")
    public ArrayList<LockRecord> findByBuildingName(@RequestParam("buildingName") String buildingName) throws MyException {
        ArrayList<LockRecord> arrayList = new ArrayList<>();  //装载返回的数据
        Date current = new Date();
        Date old = new Date(current.getTime() - 7 * 24 * 60 * 60 * 1000);
        ArrayList<LockRecord> arrayList1 = lockRecordRepository.findByBuildingName(current,old,buildingName);
        if (arrayList1.size() == 0){
            throw new MyException("no name");
        }else {
            for (int i = arrayList1.size(); i > 0; i--){
                arrayList.add(arrayList1.get(i - 1));     //按照从最新日期到七天前的日期返回
            }

            return arrayList;
        }

    }


    /**
     * 根据寝室号查找锁状态信息
     * 返回一周内的信息
     * 表格展示时间有当前时间到七天前的时间（需要倒序处理）
     * @param dormNumber
     * @return
     */
    @RequestMapping(value = "/findByDormNum")
    public ArrayList<LockRecord> findByDormNum(@RequestParam("dormNumber") String dormNumber) throws MyException {
        ArrayList<LockRecord> arrayList = new ArrayList<>();  //装载返回的数据
        Date current = new Date();
        Date old = new Date(current.getTime() - 7 * 24 * 60 * 60 * 1000);
        ArrayList<LockRecord> arrayList1 = lockRecordRepository.findByDormNum(current,old,dormNumber);
        if (arrayList1.size() == 0){
            throw new MyException("no name");
        }else {
            for (int i = arrayList1.size(); i > 0; i--){
                arrayList.add(arrayList1.get(i - 1));     //按照从最新日期到七天前的日期返回
            }
            return arrayList;
        }

    }


    /**
     * 根据楼栋信息和寝室号信息查找开锁记录
     * 返回一周以内的数据
     * 表格展示时间由当前时间到七天前的时间（需要倒序处理）
     * @param buildingName
     * @param dormNumber
     * @return
     */
    @RequestMapping(value = "/findByLocation")
    public ArrayList<LockRecord> findByLocation(@RequestParam("buildingName") String buildingName,
                                                @RequestParam("dormNumber") String dormNumber) throws MyException {
        ArrayList<LockRecord> arrayList = new ArrayList<>();  //装载返回的数据
        Date current = new Date();
        Date old = new Date(current.getTime() - 7 * 24 * 60 * 60 * 1000);
        ArrayList<LockRecord> arrayList1 = lockRecordRepository.findByBuildingNameAndDormNum(current,old,buildingName,dormNumber);
        if (arrayList1.size() == 0){
            throw new MyException("no name");
        }else {
            for (int i = arrayList1.size(); i > 0; i--){
                arrayList.add(arrayList1.get(i - 1));     //按照从最新日期到七天前的日期返回
            }
            return arrayList;
        }
    }



    /**
     * 根据学生的学号进行开锁记录查询
     * 返回能查到的所有数据信息
     * 倒序处理 从最新的数据开始展示
     * @param studentNum
     * @return
     */
    @RequestMapping(value = "/findByStudentNum")
    public ArrayList<LockRecord> findByStudentNum(@RequestParam("studentNum") String studentNum) throws MyException {

        ArrayList<LockRecord> arrayList = new ArrayList<>();
        ArrayList<LockRecord> arrayList1 = lockRecordRepository.findByStudentNum(studentNum);
        if (arrayList1.size() == 0) {
            throw new MyException("no name");
        }else {
            for (int i = arrayList1.size(); i > 0; i--){
                arrayList.add(arrayList1.get(i - 1));     //按照从最新日期到七天前的日期返回
            }
            return arrayList;
        }
    }



    /**
     * 根据日期进行查询
     * 返回当天所有的数据
     * 模糊查询
     * @param date
     * @return
     */
    @RequestMapping(value = "/findByDate")
    public ArrayList<LockRecord> findByDate(@RequestParam("date") String date) throws ParseException, MyException {
        date = date.replace("Z", " UTC");//是空格+UTC
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = utcFormat.parse(date);
        String typicalDate = defaultFormat.format(date1);
        ArrayList<LockRecord> arrayList = lockRecordRepository.findByUnlockTime(typicalDate);
        if (arrayList.size() == 0){
            throw new MyException("no name");
        }else {
            return arrayList;
        }
    }


    /**
     * 获取chart将要展示的所有信息
     * @return
     */
    @RequestMapping(value = "/getChartData")
    public ArrayList getChartData(){
        ArrayList<HashMap> arrayList = new ArrayList<>();  //装载返回的所有数据
        HashMap<String,Integer> hashMap1 = new HashMap<>();  //装载返回的今日需要换电池数量统计
        HashMap<String,Integer> hashMap2 = new HashMap<>();  //装载返回的信号强度差的寝室的数量
        HashMap<Integer,Integer> hashMap3 = new HashMap<>(); //装载返回每日使用流量（每天每个时间点开锁次数）
        HashMap<String,Integer> hashMap4 = new HashMap<>();  //装载返回一周内开锁方式数量统计
        for (int i = 0; i < 24;i++){
            hashMap3.put(i,0);
        }
        //组装第一个图和第二个图需要的数据
        HashMap<String,LockState> state = new HashMap<>();
        Date current = new Date();
        Date old = new Date(current.getTime() - 24 * 60 * 60 * 1000);
        ArrayList<LockState> arrayList2 =  lockStateRepository.getDayStateInfo(current,old);
        for (int i = arrayList2.size(); i > 0; i--){
            if (!state.containsKey(arrayList2.get(i-1).getLockId())) {  //去重
                state.put(arrayList2.get(i-1).getLockId(),arrayList2.get(i-1));
                if (arrayList2.get(i-1).getLockBattery() < 10 && hashMap1.containsKey(arrayList2.get(i-1).getBuildingName())){
                    hashMap1.put(arrayList2.get(i-1).getBuildingName(),hashMap1.get(arrayList2.get(i-1).getBuildingName())+1);
                }else if (arrayList2.get(i-1).getLockBattery() < 10 && !hashMap1.containsKey(arrayList2.get(i-1).getBuildingName())){
                    hashMap1.put(arrayList2.get(i-1).getBuildingName(),1);
                }

                if (arrayList2.get(i-1).getLockSignal() == 1 && hashMap2.containsKey(arrayList2.get(i-1).getBuildingName())){
                    hashMap2.put(arrayList2.get(i-1).getBuildingName(),hashMap2.get(arrayList2.get(i-1).getBuildingName())+1);
                }else if (arrayList2.get(i-1).getLockSignal() == 1 && !hashMap2.containsKey(arrayList2.get(i-1).getBuildingName())){
                    hashMap2.put(arrayList2.get(i-1).getBuildingName(),1);
                }
            }
        }

        //组装图三数据
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = defaultFormat.format(old);
        ArrayList<LockRecord> arrayList1 = lockRecordRepository.findByUnlockTime(date);
        for (int i = 0; i < arrayList1.size(); i++){
            if (hashMap3.containsKey(Integer.valueOf(arrayList1.get(i).getUnlockTime().toString().substring(11,13)))){
                hashMap3.put(Integer.valueOf(arrayList1.get(i).getUnlockTime().toString().substring(11,13)),hashMap3.get(Integer.valueOf(arrayList1.get(i).getUnlockTime().toString().substring(11,13)))+1);
            }
        }


        //组装图四数据
        Date old1 = new Date(current.getTime() - 7 * 24 * 60 * 60 * 1000);
        ArrayList<LockRecord> arrayList3 = lockRecordRepository.getWeekRecordInfo(current,old1);
        for (int i = 0; i < arrayList3.size();i++){
            if (hashMap4.containsKey(arrayList3.get(i).getUnlockMeasure())){
                hashMap4.put(arrayList3.get(i).getUnlockMeasure(),hashMap4.get(arrayList3.get(i).getUnlockMeasure())+1);
            }else {
                hashMap4.put(arrayList3.get(i).getUnlockMeasure(),1);
            }
        }


        arrayList.add(hashMap1);
        arrayList.add(hashMap2);
        arrayList.add(hashMap3);
        arrayList.add(hashMap4);
        return arrayList;
    }
}
