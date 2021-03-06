package com.kaka.house.web.controller;

import com.kaka.house.common.constants.CommonConstants;
import com.kaka.house.common.model.User;
import com.kaka.house.biz.service.UserService;
import com.kaka.house.common.result.ResultMsg;
import com.kaka.house.common.utils.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
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
            modelMap.put("email", account.getEmail());
            return "/user/accounts/registerSubmit";
        }else {
            return "redirect:/account/register?"+resultMsg.asUrlParams();
        }

    }

    //用户激活
    @RequestMapping("accounts/verify")
    public String verify(String key){
        boolean result = userService.enable(key);
        if (result){
            return "redirect:/index?"+ResultMsg.successMsg("激活成功！").asUrlParams();
        }else {
            return "redirect:accounts/register?"+ResultMsg.errorMsg("激活失败,请确认链接是否过期").asUrlParams();
        }
    }

    //---------------------登录模块----------------------------------------------
    @RequestMapping("accounts/signin")
    public String signin(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //目标页
        String target = req.getParameter("target");
        if (username == null || password == null){
            req.setAttribute("target", target);
            return "/user/accounts/signin";
        }
        User user = userService.auth(username,password);
        if (user == null){
            return "redirect:/accounts/signin?"+"username="+username+"&"+ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }else if (user.getEnable() == 0){
            return "redirect:/accounts/signin?"+"username="+username+"&"+ResultMsg.errorMsg("用户邮箱未验证").asUrlParams();
        }else {
            HttpSession session = req.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
            //session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
            return StringUtils.isBlank(target)?"redirect:/index":"redirect:"+target;

        }
    }

    //登出
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    //--------------------个人信息-------------------------
    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest request,User updateUser,ModelMap modelMap){
        if (updateUser.getEmail() ==null ){
            return "/user/accounts/profile";
        }
        userService.updateUser(updateUser,updateUser.getEmail());
        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> list = userService.getUserByQuery(query);
        request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, list.get(0));
        return "redirect:/accounts/profile?"+ResultMsg.successMsg("更新成功！").asUrlParams();

    }

    @RequestMapping("accounts/changePassword")
    public String changePassword(String password,String email,String newPassword,String confirmPassword,ModelMap modelMap){
        User user = userService.auth(email, password);
        if (user == null){
            return "redirect:/accounts/profile?"+ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User userUpdate = new User();
        userUpdate.setPasswd(HashUtils.encryPassword(newPassword));
        userService.updateUser(userUpdate, user.getEmail());
        return "redirect:/accounts/profile?"+ResultMsg.successMsg("更新成功").asUrlParams();
    }


}
