package com.example.demo.modules.quartz.telegramBot.function;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.modules.quartz.entity.CoinsVo;
import com.example.demo.modules.quartz.utils.Constant;
import com.example.demo.modules.quartz.utils.TRXData;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class ErCiShiJIan {

    public static String duoci(String text){
        String infoAccount = TRXData.getAccount(Constant.TRX_API, text);
        CoinsVo coinsVo = JSONObject.parseObject(infoAccount, CoinsVo.class);
        BigDecimal usdt = Convert.fromWei(coinsVo.getBalance(), Convert.Unit.MWEI);// 转换成USDT金额格式
        String fromText = "地址：\n" + coinsVo.getAddress() + "\n" + "当前TRX余额："+ usdt+ "\n" + "免费带宽已使用量："+ (coinsVo.getFree_net_usage() == null ? "" : coinsVo.getFree_net_usage());
        return fromText;
    }
}
