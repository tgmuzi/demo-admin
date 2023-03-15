package com.example.demo.modules.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.modules.user.entity.Customer;
import com.example.demo.utils.Encrypt;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

    int addCustomer(@Param("id") Long id, @Param("phone") Encrypt phone, @Param("address") String address);

    List<Customer> findCustomer(Encrypt phone);

}