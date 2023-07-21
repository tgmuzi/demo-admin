package com.example.demo.modules.user.controller;

import java.util.List;

import com.example.demo.service.modules.user.service.CustomerSrevice;
import com.example.demo.utils.AjaxObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.modules.user.entity.Customer;

@Controller
@RequestMapping("${adminPath}/customer")
public class CustomerController {

    @Autowired
    private CustomerSrevice customerSrevice;

    @RequestMapping("/save")
    @ResponseBody
    public AjaxObject save(@RequestBody Customer customer) {
        customerSrevice.addCustomer(customer);
        return AjaxObject.ok().data("新增管理员成功");
    }

    @RequestMapping("/list")
    @ResponseBody
    public AjaxObject list(@RequestBody Customer customer) {

        List<Customer> customer1 = customerSrevice.findCustomer(customer.getPhone());
        return AjaxObject.ok().data(customer1);
    }
}