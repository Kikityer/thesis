package com.hdh.smartlock.repository;

import com.hdh.smartlock.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @ClassName CustomerRepository
 * @Description TODO
 * @Author Kikityer
 * @Date 2019/6/4 16:32
 * @Version 1.0.0
 **/
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer>{

    CustomerEntity findBySchoolName(String schoolName); //根据学校名字查找学校对象

    CustomerEntity findBySchoolAddress(String schoolAddress); //根据学校地址查找学校对象

    ArrayList<CustomerEntity> findAll();

//    Optional<CustomerEntity> findById(Integer id);

}
