package com.example.demo.modules.quartz.telegramBot.function;



import com.example.demo.modules.quartz.utils.Constant;
import com.example.demo.modules.quartz.utils.TRXData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HuiLv {

    public static String huilv(){
        String infoHuiLv ="当前*1USDT兑换汇率*为：\n";
        infoHuiLv += TRXData.getsymbol(Constant.BINANCE_API, Constant.DAIBI_USDT_ADDRESS);
        infoHuiLv +="\n" + " * 以上更新时间为 * ："+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        return infoHuiLv;
    }
}
