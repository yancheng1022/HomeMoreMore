package com.kaka.house.web.controller;

import com.kaka.house.common.model.User;
import com.kaka.house.common.result.ResultMsg;
import org.apache.commons.lang3.*;

import java.util.concurrent.CountDownLatch;


public class UserHelper {
    public static ResultMsg validate(User account){

        //判断email
        if (StringUtils.isBlank(account.getEmail())){
            return ResultMsg.errorMsg("Email 有误");
        }
        if (StringUtils.isBlank(account.getName())){
            return ResultMsg.errorMsg("名字有误");
        }
        if (StringUtils.isBlank(account.getConfirmPasswd())||StringUtils.isBlank(account.getPasswd())){
            return ResultMsg.errorMsg("密码/确认密码不能为空");
        }
        if (account.getPasswd().length() < 6){
            return ResultMsg.errorMsg("密码应大于6位");
        }
        return ResultMsg.successMsg("");

    }
}
