package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.POJO.LockState;
import com.hdh.smartlock.exception.MyException;
import com.hdh.smartlock.repository.LockStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @ClassName LockStateController
 * @Description TODO  锁状态控制类
 * @Author Kikityer
 * @Date 2019/6/26 15:05
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/lockState")
public class LockStateController {

    /**
     * 构造器注入
     */
    private LockStateRepository lockStateRepository;
    @Autowired
    LockStateController(LockStateRepository lockStateRepository){
        this.lockStateRepository = lockStateRepository;
    }


    /**
     * 锁状态信息表格的初始化，默认返回一天内的所有信息
     * 返回24小时内的所有信息
     *
     * @return
     */
   @RequestMapping(value = "/getDayStateInfo")
   public ArrayList<LockState> getDayStateInfo(){

       ArrayList<LockState> arrayList = new ArrayList<>();   //装载返回的数据
       HashMap<String,LockState> hashMap = new HashMap<>();
       Date current = new Date();
       Date old = new Date(current.getTime() - 24 * 60 * 60 * 1000);
       ArrayList<LockState> arrayList1 =  lockStateRepository.getDayStateInfo(current,old);
       for (int i = arrayList1.size(); i > 0; i--){
           if (!hashMap.containsKey(arrayList1.get(i-1).getLockId())) {
               arrayList.add(arrayList1.get(i - 1));
               hashMap.put(arrayList1.get(i-1).getLockId(),arrayList1.get(i-1));
           }
       }
       return arrayList;
   }


    /**
     * 根据楼栋名称查找锁状态信息
     * 返回24小时内的消息
     * @param buildingName
     * @return
     */
   @RequestMapping(value = "/findByBuildingName")
   public ArrayList<LockState> findByBuildingName(@RequestParam("buildingName") String buildingName) throws MyException {

       ArrayList<LockState> arrayList = new ArrayList<>();  //装载返回的数据
       HashMap<String,LockState> hashMap = new HashMap<>(); //去重（过滤的作用）
       Date current = new Date();
       Date old = new Date(current.getTime() - 24 * 60 * 60 * 1000);
       ArrayList<LockState> arrayList1 =  lockStateRepository.findByBuildingName(current,old,buildingName);
       if (arrayList1.size() == 0){
           throw new MyException("no name");
       }else {
           for (int i = arrayList1.size(); i > 0; i--){
               if (!hashMap.containsKey(arrayList1.get(i-1).getLockId())) {
                   arrayList.add(arrayList1.get(i - 1));
                   hashMap.put(arrayList1.get(i-1).getLockId(),arrayList1.get(i-1));
               }

           }
           return arrayList;
       }
   }


    /**
     * 根据寝室号查找锁状态信息
     * 返回24小时内的信息
     * @param dormNumber
     * @return
     */
   @RequestMapping(value = "/findByDormNum")
   public ArrayList<LockState> findByDormNum(@RequestParam("dormNumber") String dormNumber) throws MyException {
       ArrayList<LockState> arrayList = new ArrayList<>();  //装载返回的数据
       HashMap<String,LockState> hashMap = new HashMap<>(); //去重（过滤的作用）
       Date current = new Date();
       Date old = new Date(current.getTime() - 24 * 60 * 60 * 1000);
       ArrayList<LockState> arrayList1 = lockStateRepository.findByDormNum(current,old,dormNumber);
       if (arrayList1.size() == 0){
           throw new MyException("no name");
       }else {
           for (int i = arrayList1.size(); i > 0; i--){
               if (!hashMap.containsKey(arrayList1.get(i-1).getLockId())) {
                   arrayList.add(arrayList1.get(i - 1));
                   hashMap.put(arrayList1.get(i-1).getLockId(),arrayList1.get(i-1));
               }

           }
           return arrayList;
       }
   }


    /**
     * 根据楼栋名称和寝室号返回锁状态信息
     * 只返回24小时内的信息
     * @param buildingName
     * @param dormNumber
     * @return
     */
   @RequestMapping(value = "/findByLocation")
   public ArrayList<LockState> findByLocation(@RequestParam("buildingName") String buildingName,
                                              @RequestParam("dormNumber") String dormNumber) throws MyException {
       ArrayList<LockState> arrayList = new ArrayList<>();  //装载返回的数据
       HashMap<String,LockState> hashMap = new HashMap<>(); //去重（过滤的作用）
       Date current = new Date();
       Date old = new Date(current.getTime() - 24 * 60 * 60 * 1000);
       ArrayList<LockState> arrayList1 = lockStateRepository.findByBuildingNameAndDormNum(current,old,buildingName,dormNumber);
       if (arrayList1.size() == 0){
           throw new MyException("no name");
       }else {
           for (int i = arrayList1.size(); i > 0; i--){
               if (!hashMap.containsKey(arrayList1.get(i-1).getLockId())) {
                   arrayList.add(arrayList1.get(i - 1));
                   hashMap.put(arrayList1.get(i-1).getLockId(),arrayList1.get(i-1));
               }

           }
           return arrayList;
       }
   }


    /**
     * 根据锁id对锁状态进行查询
     * 返回24小时内的数据
     * @param lockId
     * @return
     * @throws MyException
     */
   @RequestMapping(value = "/findByLockID")
   public ArrayList<LockState> findByLockID(@RequestParam("lockId") String lockId ) throws MyException {
       ArrayList<LockState> arrayList = new ArrayList<>();  //装载返回的数据
       HashMap<String,LockState> hashMap = new HashMap<>(); //去重（过滤的作用）
       Date current = new Date();
       Date old = new Date(current.getTime() - 24 * 60 * 60 * 1000);
       ArrayList<LockState> arrayList1 = lockStateRepository.findByLockId(current,old,lockId);
       if (arrayList1.size() == 0){
           throw new MyException("no name");
       }else {
           for (int i = arrayList1.size(); i > 0; i--){
               if (!hashMap.containsKey(arrayList1.get(i-1).getLockId())) {
                   arrayList.add(arrayList1.get(i - 1));
                   hashMap.put(arrayList1.get(i-1).getLockId(),arrayList1.get(i-1));
               }
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
   public ArrayList<LockState> findByDate(@RequestParam("date") String date) throws ParseException, MyException {
       date = date.replace("Z", " UTC");//是空格+UTC
       SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
       SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date date1 = utcFormat.parse(date);
       String typicalDate = defaultFormat.format(date1);
       ArrayList<LockState> arrayList = lockStateRepository.findByStateTime(typicalDate);
       if (arrayList.size() == 0){
           throw new MyException("no name");
       }else {
           return arrayList;
       }
   }


    /**
     * 远程立即开锁功能
     * 确定通信服务器的ip和端口
     * 需要后期修改通信地址
     * @param lockId
     * @return
     */
   @RequestMapping(value = "/openImmediately")
   public String openLock(@RequestParam("lockId") String lockId) throws IOException {

       final int TIMEOUT = 15000; //设置15秒超时
       StringBuilder result = new StringBuilder();
       Socket socket = null;
       socket = new Socket("172.19.2.94", 5426);  //建立一个socket对像
       socket.setSoTimeout(TIMEOUT);

       //向socket服务端发送信息
       OutputStream os = socket.getOutputStream(); //获得一个字节输出流
       BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os);
       bufferedOutputStream.write((lockId+"03ffff").getBytes());  //输出
       bufferedOutputStream.flush();
       System.out.println("发送完毕");

       //接收socket服务端返回的信息
       InputStream is = socket.getInputStream();
       InputStreamReader inputStreamReader = new InputStreamReader(is);
       BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
       String temp = "";
       if ((temp = bufferedReader.readLine())!= null) {
           result.append(temp);
       }
       System.out.println(result);

       //关闭资源
       os.close();
       bufferedOutputStream.close();
       is.close();
       inputStreamReader.close();
       bufferedReader.close();
       socket.close();

       return result.toString();
   }

}
