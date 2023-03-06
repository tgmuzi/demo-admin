package com.example.demo.modules.user.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.modules.user.entity.Customer;
import com.example.demo.utils.Encrypt;

public interface CustomerSrevice {

    int addCustomer(Customer customer);

    List<Customer> findCustomer(@Param("phone") Encrypt phone);
}