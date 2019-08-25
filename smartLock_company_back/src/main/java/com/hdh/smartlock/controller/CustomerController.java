package com.hdh.smartlock.controller;

import com.hdh.smartlock.dto.CustomerDTO;
import com.hdh.smartlock.entity.CustomerEntity;
import com.hdh.smartlock.exception.MyException;
import com.hdh.smartlock.repository.CustomerRepository;
import com.hdh.smartlock.shiro.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName CustomerController
 * @Description TODO  购买智能锁的高校相关信息控制类
 * @Author Kikityer
 * @Date 2019/6/4 16:36
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/customerInfo")
public class CustomerController {


    //构造器注入
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    /**
     * 获取所有高校名称返回给前台作为自动完成搜索框
     * @return
     */
    @RequestMapping(value = "/getSchoolName")
    public ArrayList<String> getSchoolName(){

        ArrayList<String> arrayList = new ArrayList<>();
        for (CustomerEntity one: customerRepository.findAll()) {
            String name = one.getSchoolName();
            arrayList.add(name);
        }
        return arrayList;
    }


    /**
     * 1：初始化表格信息（默认当前页为1，每页显示10条数据）
     * 2：分页查找信息（输入：当前页数和每页显示的条数）
     *
     * 分页查找
     *
     * @return
     */
    @RequestMapping(value = "/getSchoolInfo")
    public Page<CustomerEntity> getAllSchoolInfo(@RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("currentPageNum") Integer currentPageNum){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(currentPageNum-1, pageSize, Sort.Direction.ASC, "id");

        System.out.println(customerRepository.findAll(pageable));

        return customerRepository.findAll(pageable);
    }


    /**
     * 根据学校名称查找
     * 若存在直接返回找到的用户对象（json格式）
     * 若不存在返回自定义异常信息
     * @param schoolName
     * @return
     * @throws MyException
     */
    @RequestMapping(value = "/findBySchoolName")
    public ArrayList<CustomerEntity> findBySchoolName(@RequestParam("schoolName") String schoolName) throws MyException {

        ArrayList<CustomerEntity> arrayList = new ArrayList<>();

        CustomerEntity customerEntity = customerRepository. findBySchoolName(schoolName);
        if (customerEntity == null){
            throw new MyException("no name");
        }else {
            System.out.println(customerEntity.getPurchaseDate());
            arrayList.add(customerEntity);
            return arrayList;
        }
    }


    /**
     * 新增一个高校客户购买信息
     * @param customerDTO
     * @return
     */
    @RequestMapping(value = "/addCustomer")
    @Transactional
    @Deprecated
    public ReturnResult addCustomer(CustomerDTO customerDTO){

        ReturnResult result = new ReturnResult();
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setSchoolName(customerDTO.getSchoolName());
        customerEntity.setSchoolAddress(customerDTO.getSchoolAddress());
        Date date = new Date(customerDTO.getPurchaseDate());
        customerEntity.setPurchaseDate(date);
        customerEntity.setPurchaseBatch(customerDTO.getPurchaseBatch());
        customerEntity.setBatchAmount(customerDTO.getBatchAmount());
        customerEntity.setAllAmount(customerDTO.getAllAmount());
        customerRepository.save(customerEntity);
        result.setCode(200);
        result.setMsg("add success");
        return result;
    }


    /**
     * 编辑高校客户购买信息
     * @param customerDTO
     * @return
     */
    @RequestMapping(value = "/editCustomer")
    @Transactional
    @Deprecated
    public ReturnResult editCustomer(CustomerDTO customerDTO){
        ReturnResult result = new ReturnResult();
        Integer id = customerDTO.getId();
        CustomerEntity customerEntity = customerRepository.findById(id).orElse(null);
        customerEntity.setSchoolName(customerDTO.getSchoolName());
        customerEntity.setSchoolAddress(customerDTO.getSchoolAddress());
        Date date = new Date(customerDTO.getPurchaseDate());
        customerEntity.setPurchaseDate(date);
        customerEntity.setPurchaseBatch(customerDTO.getPurchaseBatch());
        customerEntity.setBatchAmount(customerDTO.getBatchAmount());
        customerEntity.setAllAmount(customerDTO.getAllAmount());
        customerRepository.save(customerEntity);
        result.setCode(200);
        result.setMsg("add success");
        return result;
    }


    /**
     * 删除一个高校客户信息
     * @return
     */
    @RequestMapping(value = "/deleteOne")
    public ReturnResult deleteOne(@RequestParam("id")  Integer  id){
        ReturnResult result = new ReturnResult();
        customerRepository.deleteById(id);
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
            customerRepository.deleteById(ids[i]);
        }
        result.setCode(200);
        result.setMsg("delete ok");
        return result;
    }


    /**
     * 获取客户信息的图表分析中的数据
     * @return
     */
    @RequestMapping(value = "/getEchartData")
    public ArrayList getEchartData(){
        ArrayList<HashMap> arrayList = new ArrayList<>();  //返回数据的集合
        HashMap<String,Integer> hashMap1 = new HashMap<>();  //装载产品销售地区数据
        HashMap<String,Integer> hashMap2 = new HashMap<>();  //装载产品销售日期数据
        HashMap<String,String> hashMap3 = new HashMap<>();  //装载产品销售高校对应的数据
        HashMap<String,Integer> hashMap4 = new HashMap<>();  //装载高校购买批次的饼状图占比图

        ArrayList<String> listArea = new ArrayList<>();
        ArrayList<String> listDate = new ArrayList<>();
        ArrayList<String> listBatch = new ArrayList<>();

        List<CustomerEntity> list = customerRepository.findAll();
        for (CustomerEntity customerEntity : list){  //遍历list中的每个元素
            hashMap3.put(customerEntity.getSchoolName(),customerEntity.getAllAmount());
            listArea.add(customerEntity.getSchoolAddress());
            listDate.add(customerEntity.getPurchaseDate().toString());
            listBatch.add(customerEntity.getPurchaseBatch());
        }
        //遍历地区数组
        for (String area: listArea){
            int a = area.indexOf('省');
            if (a != -1){
                String str1 = area.substring(0,a+1);  //有省字字符的时候，将省字字符之前的字符串截断赋值给str1，并丢进集合listProvince
                if (hashMap1.containsKey(str1)){
                    hashMap1.put(str1,hashMap1.get(str1)+1);
                }else {
                    hashMap1.put(str1,1);
                }
            }else {
                int b = area.indexOf('市');  //没有的省字的时候，直接将市字之前的字符串截断赋值给str2，并丢进集合listCity
                String str2 = area.substring(0,b+1);
                if (hashMap1.containsKey(str2)){
                    hashMap1.put(str2,hashMap1.get(str2)+1);
                }else {
                    hashMap1.put(str2,1);
                }
            }
        }
        //遍历日期数组
        for (String d : listDate){
            String years = d.substring(0,4);
            if (hashMap2.containsKey(years)){
                hashMap2.put(years, hashMap2.get(years)+1);
            }
            else {
                hashMap2.put(years,1);
            }
        }
        //遍历批次数组
        for (String batch : listBatch){
            switch (batch){
                case "1":
                    if (hashMap4.containsKey("一批次")){
                        hashMap4.put("一批次", hashMap4.get("一批次")+1);
                    }
                    else {
                        hashMap4.put("一批次",1);
                    }
                    break;
                case "2":
                    if (hashMap4.containsKey("二批次")){
                        hashMap4.put("二批次", hashMap4.get("二批次")+1);
                    }
                    else {
                        hashMap4.put("二批次",1);
                    }
                    break;
                case "3":
                    if (hashMap4.containsKey("三批次")){
                        hashMap4.put("三批次", hashMap4.get("三批次")+1);
                    }
                    else {
                        hashMap4.put("三批次",1);
                    }
                    break;
                case "4":
                    if (hashMap4.containsKey("四批次")){
                        hashMap4.put("四批次", hashMap4.get("四批次")+1);
                    }
                    else {
                        hashMap4.put("四批次",1);
                    }
                    break;
                case "5":
                    if (hashMap4.containsKey("五批次")){
                        hashMap4.put("五批次", hashMap4.get("五批次")+1);
                    }
                    else {
                        hashMap4.put("五批次",1);
                    }
                    break;
            }
        }
        arrayList.add(hashMap1);
        arrayList.add(hashMap2);
        arrayList.add(hashMap3);
        arrayList.add(hashMap4);
        return arrayList;

    }
}
