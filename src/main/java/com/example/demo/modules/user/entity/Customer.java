package com.example.demo.modules.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.example.demo.utils.Encrypt;

public class Customer extends Model<Customer> {

    private Long id;
    private Encrypt phone;
    private String address;

    /**
     * @return String return the phone
     */
    public Encrypt getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(Encrypt phone) {
        this.phone = phone;
    }

    /**
     * @return String return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}