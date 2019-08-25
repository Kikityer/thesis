package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.DTO.DormManagerInfoDTO;
import com.hdh.smartlock.entity.POJO.DormManagerInfo;
import com.hdh.smartlock.entity.VO.DormManagerInfoVO;
import com.hdh.smartlock.exception.MyException;
import com.hdh.smartlock.repository.DormManagerRepository;
import com.hdh.smartlock.util.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName DormManagerController
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/24 14:18
 * @Version 1.0.0
 **/
@RestController
@RequestMapping(value = "/dormManager")
public class DormManagerController {

    /**
     * 构造器注入
     */
    @Autowired
    private DormManagerRepository dormManagerRepository;
    DormManagerController(DormManagerRepository dormManagerRepository){
        this.dormManagerRepository = dormManagerRepository;
    }


    /**
     * 1：初始化表格信息（默认当前页为1，每页显示10条数据）
     * 2：分页查找信息（输入：当前页数和每页显示的条数）
     *
     * 分页查找
     *
     * @return
     */
    @RequestMapping(value = "/getManagerInfo")
    public HashMap<String,ArrayList> getAllSchoolInfo(@RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam("currentPageNum") Integer currentPageNum){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(currentPageNum-1, pageSize, Sort.Direction.ASC, "id");

        List<DormManagerInfo> list = dormManagerRepository.findAll(pageable).getContent();

        HashMap hashMap = new HashMap();
        ArrayList<DormManagerInfoDTO> arrayList = new ArrayList<>();  //装载搜索的内容
        ArrayList<Long> arrayList1 = new ArrayList<>();  //装载总的数据量
        arrayList1.add(dormManagerRepository.findAll(pageable).getTotalElements());
        for (DormManagerInfo dormManagerInfo : list){

            DormManagerInfoDTO dormManagerInfoDTO = new DormManagerInfoDTO();
            dormManagerInfoDTO.setId(dormManagerInfo.getId());
            dormManagerInfoDTO.setName(dormManagerInfo.getName());
            dormManagerInfoDTO.setAge(dormManagerInfo.getAge());
            dormManagerInfoDTO.setSexual(dormManagerInfo.getSexual());
            dormManagerInfoDTO.setJobNum(dormManagerInfo.getJobNum());
            dormManagerInfoDTO.setPhoneNum(dormManagerInfo.getPhoneNum());
            dormManagerInfoDTO.setBuildingName(dormManagerInfo.getBuildingName());
            dormManagerInfoDTO.setRole(dormManagerInfo.getRole());
            dormManagerInfoDTO.setRegistered(dormManagerInfo.getRegistered());
            arrayList.add(dormManagerInfoDTO);
        }
        hashMap.put("content",arrayList);
        hashMap.put("totalElements",arrayList1);
        return hashMap;
    }


    /**
     * 根据宿管名字实现模糊查询
     * @param managerName
     * @return
     */
    @RequestMapping(value = "/findByManagerName")
    public ArrayList<DormManagerInfo> findByManagerName(@RequestParam("managerName") String managerName) throws MyException {

        ArrayList<DormManagerInfo> arrayList;
        arrayList = dormManagerRepository.findByName(managerName);
        if (arrayList.size() == 0){
            throw new MyException("no name");
        }else {
            return arrayList;
        }
    }


    /**
     * 根据宿管工号实现模糊查询
     * @return
     */
    @RequestMapping(value = "/findByManagerJobNum")
    public ArrayList<DormManagerInfo> findByManagerJobNum(@RequestParam("managerJobNum") String managerJobNum) throws MyException {

        ArrayList<DormManagerInfo> arrayList;
        arrayList =  dormManagerRepository.findByJobNum(managerJobNum);
        if (arrayList.size() == 0){
            throw new MyException("no number");
        }else {
            return arrayList;
        }
    }


    /**
     * 新增一个宿管信息
     * @param dormManagerInfoVO
     * @return
     */
    @RequestMapping(value = "/addManagerInfo")
    @Transactional
    public ReturnResult addManagerInfo(DormManagerInfoVO dormManagerInfoVO){

        ReturnResult result = new ReturnResult();
        DormManagerInfo dormManagerInfo = new DormManagerInfo();
        dormManagerInfo.setName(dormManagerInfoVO.getName());
        dormManagerInfo.setAge(dormManagerInfoVO.getAge());
        dormManagerInfo.setSexual(dormManagerInfoVO.getSexual());
        dormManagerInfo.setJobNum(dormManagerInfoVO.getJobNum());
        dormManagerInfo.setJobNumPass(dormManagerInfoVO.getJobNumPass());
        dormManagerInfo.setPhoneNum(dormManagerInfoVO.getPhoneNum());
        dormManagerInfo.setBuildingName(dormManagerInfoVO.getBuildingName());
        dormManagerInfo.setRole(dormManagerInfoVO.getRole());
        dormManagerInfo.setRegistered(dormManagerInfoVO.getRegistered());
        dormManagerRepository.save(dormManagerInfo);
        result.setCode(200);
        result.setMsg("add success");
        return result;
    }


    /**
     * 编辑一个宿管信息
     * @param dormManagerInfoVO
     * @return
     */
    @RequestMapping(value = "/editManagerInfo")
    @Transactional
    public ReturnResult editManagerInfo(DormManagerInfoVO dormManagerInfoVO){
        ReturnResult result = new ReturnResult();
        Integer id = dormManagerInfoVO.getId();
        DormManagerInfo dormManagerInfo = dormManagerRepository.findById(id).orElse(null);
        dormManagerInfo.setName(dormManagerInfoVO.getName());
        dormManagerInfo.setAge(dormManagerInfoVO.getAge());
        dormManagerInfo.setSexual(dormManagerInfoVO.getSexual());
        dormManagerInfo.setJobNum(dormManagerInfoVO.getJobNum());
        dormManagerInfo.setPhoneNum(dormManagerInfoVO.getPhoneNum());
        dormManagerInfo.setBuildingName(dormManagerInfoVO.getBuildingName());
        dormManagerInfo.setRole(dormManagerInfoVO.getRole());
        dormManagerRepository.save(dormManagerInfo);
        result.setCode(200);
        result.setMsg("add success");
        return result;
    }


    /**
     * 删除一个高校客户信息
     * @return
     */
    @RequestMapping(value = "/deleteOne")
    @Transactional
    public ReturnResult deleteOne(@RequestParam("id")  Integer  id){
        ReturnResult result = new ReturnResult();
        dormManagerRepository.deleteById(id);
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
            dormManagerRepository.deleteById(ids[i]);
        }
        result.setCode(200);
        result.setMsg("delete ok");
        return result;
    }
}
