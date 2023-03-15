package com.example.demo.modules.user.service.impl;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.modules.user.dao.CustomerMapper;
import com.example.demo.modules.user.entity.Customer;
import com.example.demo.modules.user.service.CustomerSrevice;
import com.example.demo.utils.Encrypt;

@Service
public class CustomerSreviceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerSrevice {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public int addCustomer(Customer customer) {
        return customerMapper.insert(customer);
    }

    @Override
    @DS("slave")
    public List<Customer> findCustomer(Encrypt phone) {
        return customerMapper.findCustomer(phone);
    }

}