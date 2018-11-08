package com.kaka.house.web.controller;

import com.kaka.house.common.model.User;
import com.kaka.house.biz.service.UserService;
import com.kaka.house.common.result.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("index")
    public String index(){
        return "homepage/index";
    }

    //注册提交：注册验证 发送邮件 验证失败重定向到注册页面
    //注册页获取：根据account对象是否为空 判断是否是注册页
    @RequestMapping("accounts/register")
    public String accountRegister(User account,ModelMap modelMap){
        //页面跳转
        if (account == null || account.getName() == null){
            return "/user/accounts/register";
        }
        //用户验证
        ResultMsg resultMsg = UserHelper.validate(account);
        if(resultMsg.isSuccess() && userService.addAccount(account)){
            return "/user/accounts/registerSubmit";
        }else {
            return "redirect:/account/register?"+resultMsg.asUrlParams();
        }

    }
}
