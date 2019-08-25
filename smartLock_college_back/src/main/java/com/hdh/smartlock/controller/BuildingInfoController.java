package com.hdh.smartlock.controller;

import com.hdh.smartlock.entity.POJO.BuildingInfo;
import com.hdh.smartlock.entity.VO.BuildingInfoVO;
import com.hdh.smartlock.exception.MyException;
import com.hdh.smartlock.repository.BuildingInfoRepository;
import com.hdh.smartlock.util.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * @ClassName BuildingInfoController
 * @Description TODO  楼宇信息控制类
 * @Author Kikityer
 * @Date 2019/6/22 17:42
 * @Version 1.0.0
 **/

@RestController
@RequestMapping(value = "/building")
public class BuildingInfoController {

    /**
     * 构造器注入
     */
    @Autowired
    private BuildingInfoRepository buildingInfoRepository;
    BuildingInfoController(BuildingInfoRepository buildingInfoRepository){
        this.buildingInfoRepository = buildingInfoRepository;
    }



    /**
     * 获取所有楼宇名称返回给前台作为自动完成搜索框
     * @return
     */
    @RequestMapping(value = "/getBuildingName")
    public ArrayList<String> getSchoolName(){

        ArrayList<String> arrayList = new ArrayList<>();
        for (BuildingInfo one: buildingInfoRepository.findAll()) {
            String name = one.getBuildingName();
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
    @RequestMapping(value = "/getBuildingInfo")
    public Page<BuildingInfo> getAllSchoolInfo(@RequestParam("pageSize") Integer pageSize,
                                               @RequestParam("currentPageNum") Integer currentPageNum){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(currentPageNum-1, pageSize, Sort.Direction.ASC, "id");

        return buildingInfoRepository.findAll(pageable);
    }




    /**
     * 根据楼宇名称查找楼宇信息
     * @param buildingName
     * @return
     * @throws MyException
     */
    @RequestMapping(value = "/findByBuildingName")
    public ArrayList<BuildingInfo> findByBuildingName(@RequestParam("buildingName") String buildingName) throws MyException {

        ArrayList<BuildingInfo> arrayList = new ArrayList<>();
        BuildingInfo buildingInfo = buildingInfoRepository.findByBuildingName(buildingName);
        if (buildingInfo == null){
            throw new MyException("no name");
        }else {
            arrayList.add(buildingInfo);
            return arrayList;
        }
    }


    /**
     * 根据宿管名字查找楼宇信息（模糊查找）
     * @param managerName
     * @return
     * @throws MyException
     */
    @RequestMapping(value = "/findByManagerName")
    public ArrayList<BuildingInfo> findByManagerName(@RequestParam("managerName") String managerName) throws MyException {

        ArrayList<BuildingInfo> arrayList;

        arrayList = buildingInfoRepository.findByManagerName(managerName);
        if (arrayList.size() == 0){
            throw new MyException("no name");
        }else {
            return arrayList;
        }

    }


    /**
     * 新增一个楼宇信息
     * @param buildingInfoVO
     * @return
     */
    @RequestMapping(value = "/addBuildingInfo")
    @Transactional
    public ReturnResult addBuildingInfo(BuildingInfoVO buildingInfoVO){

        ReturnResult result = new ReturnResult();
        BuildingInfo buildingInfo = new BuildingInfo();
        buildingInfo.setBuildingName(buildingInfoVO.getBuildingName());
        buildingInfo.setLockNum(buildingInfoVO.getLockNum());
        buildingInfo.setForWhom(buildingInfoVO.getForWhom());
        buildingInfo.setDormCapacity(buildingInfoVO.getDormCapacity());
        buildingInfo.setManagerName(buildingInfoVO.getManagerName());
        buildingInfoRepository.save(buildingInfo);
        result.setCode(200);
        result.setMsg("add success");
        return result;
    }


    /**
     * 编辑楼宇信息
     * @param buildingInfoVO
     * @return
     */
    @RequestMapping(value = "/editBuildingInfo")
    @Transactional
    public ReturnResult editBuildingInfo(BuildingInfoVO buildingInfoVO){
        ReturnResult result = new ReturnResult();
        Integer id = buildingInfoVO.getId();
        BuildingInfo buildingInfo = buildingInfoRepository.findById(id).orElse(null);
        buildingInfo.setBuildingName(buildingInfoVO.getBuildingName());
        buildingInfo.setLockNum(buildingInfoVO.getLockNum());
        buildingInfo.setForWhom(buildingInfoVO.getForWhom());
        buildingInfo.setDormCapacity(buildingInfoVO.getDormCapacity());
        buildingInfo.setManagerName(buildingInfoVO.getManagerName());
        buildingInfoRepository.save(buildingInfo);
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
        buildingInfoRepository.deleteById(id);
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
            buildingInfoRepository.deleteById(ids[i]);
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
        HashMap<String,Integer> hashMap1 = new HashMap<>();  //装载每栋类对应锁的数量
        HashMap<String,Integer> hashMap2 = new HashMap<>();  //装载男女生宿舍数量

        ArrayList<String> listForWhom = new ArrayList<>();

        List<BuildingInfo> list = buildingInfoRepository.findAll();
        for (BuildingInfo buildingInfo : list){  //遍历list中的每个元素
           hashMap1.put(buildingInfo.getBuildingName(),buildingInfo.getLockNum());
           listForWhom.add(buildingInfo.getForWhom());
        }

        for (String forWhom: listForWhom){
            switch (forWhom){
                case "男":
                    if (hashMap2.containsKey("男寝")){
                        hashMap2.put("男寝", hashMap2.get("男寝")+1);
                    }
                    else {
                        hashMap2.put("男寝",1);
                    }
                    break;
                case "女":
                    if (hashMap2.containsKey("女寝")){
                        hashMap2.put("女寝", hashMap2.get("女寝")+1);
                    }
                    else {
                        hashMap2.put("女寝",1);
                    }
                    break;
                case "男/女":
                    if (hashMap2.containsKey("男女混寝")){
                        hashMap2.put("男女混寝", hashMap2.get("男女混寝")+1);
                    }
                    else {
                        hashMap2.put("男女混寝",1);
                    }
                    break;
            }
        }
        arrayList.add(hashMap1);
        arrayList.add(hashMap2);
        return arrayList;
    }
}
