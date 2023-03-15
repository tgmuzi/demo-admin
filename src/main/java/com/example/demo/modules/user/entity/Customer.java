package com.example.demo.modules.user.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.example.demo.utils.Encrypt;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer extends Model<Customer> {

    @TableId("ID")
    private Long id;
    @TableField("PHONE")
    private Encrypt phone;
    @TableField("ADDRESS")
    private String address;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}